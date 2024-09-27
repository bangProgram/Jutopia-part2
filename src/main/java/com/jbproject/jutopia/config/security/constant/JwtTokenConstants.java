package com.jbproject.jutopia.config.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtTokenConstants {

    ACCESS("access","X-Access-Token"),
    REFRESH("access","X-Refresh-Token"),
    ;

    private final String name;
    private final String key;
}
