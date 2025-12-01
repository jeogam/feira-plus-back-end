package br.com.ifba.feiraplus.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe de Configuração Global da Aplicação.
 * <p>
 * Diferente do SecurityConfig (que define regras de rotas), esta classe serve para
 * criar e expor "Beans" (componentes) utilitários que serão usados em outras partes do sistema,
 * como o codificador de senhas e o gerenciador de autenticação.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Define qual estratégia de codificação de senha o sistema usará.
     * <p>
     * ATENÇÃO: Estamos usando {@link NoOpPasswordEncoder}.
     * Isso significa que NÃO HÁ criptografia. As senhas são salvas e comparadas em texto puro (ex: "123456").
     * <p>
     * Motivo: Facilita testes e desenvolvimento inicial ou correções de banco de dados.
     * Em PRODUÇÃO, isso deve ser trocado por {@code return new BCryptPasswordEncoder();}.
     *
     * @return Uma instância de encoder que não faz nada (No Operation).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Exporta o {@link AuthenticationManager} como um Bean gerenciável.
     * @param authenticationConfiguration A configuração automática do Spring Security.
     * @return O objeto que sabe processar login (chamar o UserDetailsService e verificar senha).
     * @throws Exception caso não consiga recuperar o manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}