package com.jbproject.jutopia.config.security.jwt.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ToString
@ConfigurationProperties(prefix = "jutopia.refresh.jwt")
public class RefreshJwtProperties {
    private String secret;
}
