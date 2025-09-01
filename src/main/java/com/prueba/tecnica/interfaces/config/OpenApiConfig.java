package com.prueba.tecnica.interfaces.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        var info = new Info()
                .title("TechTest API")
                .version("1.0.0")
                .description("API técnica para franquicias, sucursales y productos.")
                .contact(new Contact().name("Equipo Tech").email("equipo@example.com"))
                .license(new License().name("MIT"));

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"));
    }
}

