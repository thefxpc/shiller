package com.examen.shiller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final Contact DEFAULT_CONTACT = new Contact("Francisco Xavier Pérez Chávez",
            "https://thefxpc.com", "hola@thefxpc.com");
    private static final ApiInfo DEFAULT_API_INFO =new ApiInfo("ShillerAmerica Exam Api Documentation",
            "API developed to apply for the BackEnd developer job",
            "0.1",
            "urn:tos",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList());
    private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(Arrays.asList("application/json"));


    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).
                apiInfo(DEFAULT_API_INFO).
                produces(DEFAULT_PRODUCES_CONSUMES)
                .consumes(DEFAULT_PRODUCES_CONSUMES);
    }
}
