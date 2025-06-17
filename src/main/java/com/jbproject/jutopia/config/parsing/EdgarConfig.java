package com.jbproject.jutopia.config.parsing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EdgarConfig {

    @Bean
    public WebClient edgarWebClient() {
        return WebClient.builder()
                .baseUrl("https://data.sec.gov")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                // 자신/연락처 명시 (Fair-Access 지침)
                .defaultHeader(HttpHeaders.USER_AGENT, "jutopia/0.1 (junbyuonguk@gmail.com)")
                .build();
    }
}