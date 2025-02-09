package com.example.cors.properties;

import com.example.cors.properties.allowed.CorsAllowedProperties;
import com.example.cors.properties.exposed.CorsExposedProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("demo.webmvc.cors") // 빈 등록 (속성에서 루트로 삼는 곳)
@ConfigurationPropertiesBinding // 데이터 바인딩
public record CorsProperties(
        @NestedConfigurationProperty CorsAllowedProperties allowed,
        @NestedConfigurationProperty CorsExposedProperties exposed,
        Long maxAge
) {
}
