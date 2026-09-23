package com.samuca.lanchehub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("LancheHub API")
                        .version("0.5.0")
                        .description("API REST do LancheHub, uma plataforma para gerenciamento de lanches e pedidos. " +
                                "A API fornece recursos para cadastrar, consultar, atualizar e remover produtos, " +
                                "além de gerenciar pedidos e demais operações relacionadas ao funcionamento da plataforma." +
                                "\n" +
                                "Desenvolvida com Spring Boot, " +
                                "a API segue princípios de arquitetura REST e disponibiliza sua documentação " +
                                "e endpoints através do OpenAPI/Swagger."
                        )
                        .contact(new Contact()
                                .url("https://samuca-surf.github.io/")
                                .email("samuel.lkm.dev@gmail.com")
                        )
                );
    }
}
