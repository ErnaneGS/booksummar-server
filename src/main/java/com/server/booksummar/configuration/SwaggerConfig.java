package com.server.booksummar.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Book Summary")
                        .description("API de gerenciamento de resumos de livros")
                        .version("0.0.1")
                        .termsOfService("Termo de uso: Open Source")
                        .license(new License()
                                .name("Licença não definida")
                                .url("https://github.com/ErnaneGS/booksummar-server/tree/main")
                        )
                ).externalDocs(
                        new ExternalDocumentation()
                                .description("Readme")
                                .url("https://github.com/ErnaneGS/booksummar-server/tree/main"));
    }
}