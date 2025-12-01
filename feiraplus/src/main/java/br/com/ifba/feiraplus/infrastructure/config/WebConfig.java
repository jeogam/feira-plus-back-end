package br.com.ifba.feiraplus.infrastructure.config; // <--- ADICIONE ISSO

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas as rotas
                .allowedOrigins("http://localhost:5173") // Permite seu frontend React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}