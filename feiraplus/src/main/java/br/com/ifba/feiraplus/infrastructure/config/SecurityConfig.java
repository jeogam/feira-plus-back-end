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
                    // Desabilitamos o CSRF porque a nossa API é Stateless (não usa sessões/cookies)
                    .csrf(csrf -> csrf.disable())

                    // Definimos que a gestão de sessão é STATELESS (o servidor não guarda estado do login)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                    .authorizeHttpRequests(authorize -> authorize
                            // PERMITIR LOGIN: O endpoint de login deve ser público para que se possa gerar o token
                            .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()

                            // BLOQUEAR O RESTO: Qualquer outra rota exige autenticação (Token válido)
                            .anyRequest().authenticated()
                    )

                    // ADICIONAR FILTRO: Colocamos o nosso filtro JWT antes do filtro padrão do Spring
                    // para que o token seja validado antes de o Spring tentar qualquer outra coisa.
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

    }