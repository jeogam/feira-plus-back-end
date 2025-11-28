package br.com.ifba.feiraplus.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Serviço responsável por toda a manipulação do Token JWT.
 * <p>
 * Suas responsabilidades são:
 * 1. GERAR tokens (criar o "crachá" digital) quando o usuário loga.
 * 2. LER tokens (extrair o email/usuário) quando o usuário faz uma requisição.
 * 3. VALIDAR tokens (verificar se não expirou e se a assinatura é original).
 */
@Service
public class JwtTokenService {

    // Tempo de validade do token: 10 horas (em milissegundos)
    // Cálculo: 1000ms * 60s * 60m * 10h
    private static final long EXPIRATION_TIME_MS = 10 * 60 * 60 * 1000;

    /**
     * A "Chave Mestra" da sua aplicação.
     * Quem tiver essa String consegue gerar tokens falsos e se passar por qualquer usuário.
     * Ela é injetada do arquivo application.properties para não ficar "chumbada" no código.
     */
    @Value("${api.security.token.secret}")
    private String secretKey;

    // --- Geração do Token ---

    /**
     * Gera um token a partir de um objeto de Autenticação do Spring Security.
     * Usado geralmente no Controller de Login.
     */
    public String generateToken(Authentication authentication) {
        // Pega o usuário logado (Principal)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Delega para o método que gera baseada em String
        return generateToken(userDetails.getUsername());
    }

    /**
     * Constrói a String do JWT (aquele texto "eyJhbGcF...").
     *
     * @param username O email ou login do usuário (será o "Subject" do token).
     * @return A String do token assinado.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                // .setSubject define "quem é o dono" deste token
                .setSubject(username)

                // Data de criação (agora)
                .setIssuedAt(new Date(System.currentTimeMillis()))

                // Data de expiração (agora + 10 horas)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))

                // Assina o token com nossa chave secreta usando algoritmo HS256 (HMAC + SHA256).
                // Isso garante que se alguém alterar o token, a assinatura muda e o token fica inválido.
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)

                // .compact() transforma o objeto JSON em uma String Base64 URL-safe
                .compact();
    }

    // --- Validação e extração de dados do Token ---

    /**
     * Extrai o login (email) de dentro do token.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Valida se o token é legítimo para o usuário informado.
     *
     * @param token O token JWT recebido na requisição.
     * @param userDetails O usuário encontrado no banco de dados.
     * @return true se o token pertence ao usuário E não está expirado.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        // Verifica 1: O email no token é o mesmo do usuário do banco?
        // Verifica 2: O token ainda está no prazo de validade?
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // --- Métodos auxiliares (Internos) ---

    /**
     * Converte a String da chave secreta em um objeto criptográfico SecretKey.
     */
    private SecretKey getSigningKey() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("A chave secreta JWT não está configurada!");
        }
        // Transforma a string em bytes (UTF-8)
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        // Cria a chave compatível com o algoritmo HMAC-SHA
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Método genérico para extrair qualquer informação (Claim) do token.
     * * @param claimsResolver Uma função que diz QUAL informação queremos pegar (ex: Subject, Expiration).
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser() // Inicia o leitor de token
                .setSigningKey(getSigningKey()) // Configura a chave para validar a assinatura
                .build()
                .parseClaimsJws(token) // <--- AQUI A MÁGICA ACONTECE:
                // Se a assinatura for inválida ou o token alterado,
                // este método lança uma Exception e para tudo.
                .getBody(); // Pega o corpo do JSON (o payload)

        return claimsResolver.apply(claims);
    }

    /**
     * Verifica se a data de expiração do token já passou.
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        // Retorna true se a data de expiração for ANTES da data/hora atual
        return expiration.before(new Date());
    }
}