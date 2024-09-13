package com.jbproject.jutopia.config.security.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jutopia.refresh.jwt")
public class RefreshJwtProperties {
    private String secret;
}
