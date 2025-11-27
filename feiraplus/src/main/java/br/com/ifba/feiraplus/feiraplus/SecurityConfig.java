package br.com.ifba.feiraplus.feiraplus;

import org.springframework.beans.factory.annotation.Value; // <--- Importa isso
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // Aqui o Spring injeta os valores do arquivo de configuração
  @Value("${spring.security.user.name}")
  private String username;

  @Value("${spring.security.user.password}")
  private String password;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
            .username(username)
            .password("{noop}" + password)
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(user);
  }
}