package com.jbproject.jutopia.config.security.jwt.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ToString
@ConfigurationProperties(prefix = "jutopia.access.jwt")
public class AccessJwtProperties {
    private String secret;
    private String duration;
}
