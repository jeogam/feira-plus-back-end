package br.com.ifba.feiraplus.infrastructure.config;

import br.com.ifba.feiraplus.infrastructure.jwt.JwtAuthorizarionFilter;
// import lombok.RequiredArgsConstructor; // <--- REMOVIDO
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// @RequiredArgsConstructor // <--- O LOMBOK ESTAVA FALHANDO AQUI
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthorizarionFilter securityFilter;

    // --- CORREÇÃO: CONSTRUTOR MANUAL PARA INJEÇÃO DE DEPENDÊNCIA ---
    public SecurityConfig(JwtAuthorizarionFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // HABILITA CORS
                .cors(cors -> {})

                // Sem sessões e CSRF desabilitado
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                    // 1. Permite OPTIONS para CORS
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    
                    // 2. Rotas públicas de Autenticação e Usuário
                    .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuarios/register").permitAll()
                    
                    // 3. Rotas públicas de Consulta de Feiras
                    .requestMatchers(HttpMethod.GET, "/feiras/eventos", "/feiras/eventos/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/feiras/permanentes", "/feiras/permanentes/**").permitAll()
                    .requestMatchers("/feiras/pesquisar").permitAll()

                    // 4. LIBERAÇÃO DOS EVENTOS (Ticket SCRUM-22)
                    .requestMatchers(HttpMethod.GET, "/eventos/**").permitAll() 
                    .requestMatchers(HttpMethod.POST, "/eventos/cadastrar").hasAnyRole("ADMIN", "COORDENADOR")

                    // 5. LIBERAÇÃO DO SWAGGER
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                    // 6. SEMPRE POR ÚLTIMO: Qualquer outra requisição exige autenticação
                    .anyRequest().authenticated()
                )


                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}