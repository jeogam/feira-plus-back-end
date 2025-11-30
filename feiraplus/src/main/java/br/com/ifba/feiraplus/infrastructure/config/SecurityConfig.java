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

            @Bean
                public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                    return http
                            .csrf(csrf -> csrf.disable())
                            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            .authorizeHttpRequests(authorize -> authorize
                                    // Rotas públicas originais (Login e Cadastro de Usuário)
                                    .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                                    // BLOQUEAR O RESTO
                                    .anyRequest().authenticated()
                            )
                            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                            .build();
                }

    }