//package com.pasportes.validacion.config;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .directModelSubstitute(LocalDate.class, Date.class)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.pasportes.validacion"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("validation-service")
//                .description("Microservice to validate a person's legal profile")
//                .version("1.0.0-SNAPSHOT")
//                .build();
//    }
//    
//    public restlet
//
//}