package dev.test.bm.jwt.services;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

    private String secretKey = "dstr=!$#$key,ThaTis%topSecret1648158!15";
    private long validityInMs = 3600000; // 1h

}
