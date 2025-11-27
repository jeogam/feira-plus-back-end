package br.com.ifba.feiraplus.feiraplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; // Importante
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // Importante

@SpringBootApplication
@ComponentScan(basePackages = "br.com.ifba.feiraplus") // Acha Controller, Service, Component
@EnableJpaRepositories(basePackages = "br.com.ifba.feiraplus") // Acha os Repository
@EntityScan(basePackages = "br.com.ifba.feiraplus") // Acha as Entities (@Entity)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
