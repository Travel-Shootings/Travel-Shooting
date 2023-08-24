package com.sparta.travelshooting.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("여행 계획 서비스 API")
                .version("0.0.3")
                .description("여행 계획을 세우고 공유하는 서비스의 API입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
