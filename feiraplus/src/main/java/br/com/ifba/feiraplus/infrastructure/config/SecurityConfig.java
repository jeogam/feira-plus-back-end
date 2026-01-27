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
                        // Permite OPTIONS para CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        
                        // Rotas públicas (Login/Registro)
                        .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/register").permitAll()

                        // --- LIBERA O SWAGGER ---
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // --- LIBERA A BUSCA (Para facilitar seus testes) ---
                        .requestMatchers("/feiras/pesquisar").permitAll()

                        // Rotas protegidas (todas as outras)
                        .anyRequest().authenticated()
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}