package br.com.ifba.feiraplus.infrastructure.jwt;

import br.com.ifba.feiraplus.features.usuario.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de Autorização JWT.
 * * Este componente intercepta TODAS as requisições HTTP que chegam na API.
 * Ele verifica se existe um token JWT válido no cabeçalho.
 * Se o token for válido, ele autentica o usuário no contexto do Spring Security.
 * * Herda de OncePerRequestFilter para garantir que execute apenas uma vez por requisição.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthorizarionFilter extends OncePerRequestFilter {

    // Serviços necessários para extrair dados do token e buscar o usuário no banco
    private final JwtTokenService jwtTokenService;
    private final UsuarioService usuarioService;

    /**
     * Método principal do filtro. É aqui que a mágica acontece.
     *
     * @param request A requisição HTTP que chegou (contém headers, body, etc).
     * @param response A resposta que será enviada (caso queiramos bloquear aqui).
     * @param filterChain A corrente de filtros (permite passar a requisição adiante).
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Busca o cabeçalho "Authorization" da requisição
        final String authHeader = request.getHeader("Authorization");

        // 2. Verificação inicial: O cabeçalho existe? Começa com "Bearer "?
        // Se não tiver token, apenas deixa a requisição passar.
        // (Se a rota for protegida, o Spring Security vai barrar mais na frente).
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Limpeza: Remove o prefixo "Bearer " para pegar apenas a string do token
        final String token = authHeader.substring(7);
        final String email;

        try {
            // 4. Tenta extrair o email (subject) de dentro do token
            email = jwtTokenService.getUsernameFromToken(token);
        } catch (Exception e) {
            // Se o token estiver expirado, malformado ou adulterado, ignoramos
            // e passamos a requisição adiante (sem autenticar).
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Verifica se conseguimos extrair o email E se o usuário já não está autenticado
        // (SecurityContextHolder.getContext().getAuthentication() == null)
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Busca os detalhes do usuário no banco de dados
            UserDetails userDetails = usuarioService.loadUserByEmail(email);

            // 7. Valida matematicamente se o token pertence a esse usuário e não expirou
            if (jwtTokenService.validateToken(token, userDetails)) {

                // 8. CRIA A AUTENTICAÇÃO
                // Cria um objeto que representa o usuário logado com suas permissões (authorities)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Senha não é necessária aqui, pois já validamos o token
                        userDetails.getAuthorities()
                );

                // Adiciona detalhes extras da requisição (como IP e ID da sessão)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 9. IMPORTANTE: Informa ao Spring Security que este usuário está autenticado.
                // A partir desta linha, o usuário tem acesso às rotas protegidas.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 10. Passa a requisição para o próximo filtro ou para o Controller
        filterChain.doFilter(request, response);
    }
}