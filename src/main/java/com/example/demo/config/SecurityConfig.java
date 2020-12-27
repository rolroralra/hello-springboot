package com.example.demo.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;


@ConfigurationProperties(prefix = "security")
@Configuration
@Validated
@Getter
@Setter
@NoArgsConstructor
public class SecurityConfig {
    @DecimalMax(value = "60")
    @DecimalMin(value = "1")
    private Long tokenExpirationMinute;

//    @NotEmpty
//    @Size(min = 1, max = 10)
//    private String propertiesKey;
}
