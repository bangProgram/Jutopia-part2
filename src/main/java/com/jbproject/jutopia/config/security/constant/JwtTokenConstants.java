package com.jbproject.jutopia.config.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtTokenConstants {

    ACCESS("X-Access-Token","access"),
    REFRESH("X-Refresh-Token","refresh"),
    ;

    private final String key;
    private final String name;
}
