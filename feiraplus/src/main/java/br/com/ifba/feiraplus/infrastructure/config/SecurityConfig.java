package br.com.ifba.feiraplus.infrastructure.config;

import br.com.ifba.feiraplus.infrastructure.jwt.JwtAuthorizarionFilter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthorizarionFilter securityFilter;

    // 1. 🔥 CORREÇÃO: ADICIONAR DE VOLTA O @Bean
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
                        // Rotas públicas
                        .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/register").permitAll()
                        // Rotas protegidas
                        .anyRequest().authenticated()
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                // 2. 🔥 CORREÇÃO: ADICIONAR DE VOLTA O .build()
                .build();
    }
}