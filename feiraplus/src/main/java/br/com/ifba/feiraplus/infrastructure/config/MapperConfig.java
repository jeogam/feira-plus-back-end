package br.com.ifba.feiraplus.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    // esta classe é para poder usasr o mapper sem criar varias instancias

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
