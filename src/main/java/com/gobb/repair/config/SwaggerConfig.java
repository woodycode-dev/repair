package com.gobb.repair.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "Bearer Authentication";

        return new OpenAPI()
                .info(new Info()
                        .title("Repair API")
                        .description("부품 재고·주문 관리 REST API")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .tags(List.of(
                        new Tag().name("1. 인증").description("로그인/로그아웃/토큰 재발급 API"),
                        new Tag().name("2-1. 부품분류").description("부품분류 관련 API (CM_BIKE_ITEM_CD)"),
                        new Tag().name("2-2. 부품").description("부품 관련 API (CM_BIKE_ITEM)"),
                        new Tag().name("2-3. 부품상세").description("부품상세 관련 API (CM_BIKE_ITEM_DTL)"),
                        new Tag().name("3-1. 거래주문").description("거래주문 관련 API (TR_ORDER)"),
                        new Tag().name("3-2. 거래주문항목").description("거래주문항목 관련 API (TR_ORDER_DTL)")
                ));
    }
}