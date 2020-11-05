package com.nineleaps.greytHRClone.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Greythr clone")
                        .description("Application for displaying employee details and tracking attendance info")
                        .version("0.0.1")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact().name(""+"Shinaz |"+" Hithesh "+" | Anshu").email("greythrclone1@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))


                );
    }
}
