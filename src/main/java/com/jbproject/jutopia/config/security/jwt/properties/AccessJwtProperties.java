package com.jbproject.jutopia.config.security.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jutopia.access.jwt")
public class AccessJwtProperties {
    private String secret;
    private String duration;
}
