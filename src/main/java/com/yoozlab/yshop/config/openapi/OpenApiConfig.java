package com.yoozlab.yshop.config.openapi;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${spring.application.description}")
    private String description;

    @Value("${spring.application.version}")
    private String version;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(serviceName).description(description));
    }

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group(ApiVersion.V1)
                .packagesToScan("com.yoozlab.yshop.adapter.ws.v1")
                .build();
    }

    @Bean
    public GroupedOpenApi adminV1() {
        return GroupedOpenApi.builder()
                .group(ApiVersion.ADMIN_V1)
                .packagesToScan("com.yoozlab.yshop.adapter.ws.admin.v1")
                .build();
    }

}
