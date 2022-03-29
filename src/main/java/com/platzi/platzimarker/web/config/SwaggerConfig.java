package com.platzi.platzimarker.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2) // Especificamos que metodo de documentacion vamos a utilizar
                .select() // Indicamos que archivos va exponer en la documentacion (Solo se deberian exponer los endpoints)
                .apis(RequestHandlerSelectors.basePackage("com.platzi.platzimarker.web.controller"))
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build(); // Construye la respuesta
    }

}
