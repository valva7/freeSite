package com.web.freesite.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("free Site API")
                .description("free Site API")
                .version("1.0.0");
    }

    /*
        API 그룹 설정
    */
    @Bean
    public GroupedOpenApi demo() {
        String[] paths = {"/demo/**", "/test/**"};
        return GroupedOpenApi.builder().group("demo").pathsToMatch(paths)
                .build();
    }


}