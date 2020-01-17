package com.swang.jpaweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;
import java.util.Optional;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
  private static final String AUTH = "basicAuth";

  @Bean
  public Docket api() {
    SecurityReference reference = SecurityReference.builder().reference(AUTH)
            .scopes(new AuthorizationScope[0]).build();
    return new Docket(DocumentationType.SWAGGER_2)
            .securitySchemes(Arrays.asList(new BasicAuth(AUTH)))
            .securityContexts(Arrays.asList(SecurityContext.builder()
                    .securityReferences(Arrays.asList(reference)).build()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.swang.jpaweb.web"))
            .paths(PathSelectors.any())
            .build()
            .genericModelSubstitutes(Optional.class);
  }
}