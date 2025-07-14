package com.jbproject.jutopia.common.parsing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class EdgarConfig {

    @Bean
    public WebClient edgarWebClient() {
        HttpClient httpClient = HttpClient.create().wiretap(true);
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://data.sec.gov")
                .defaultHeader(HttpHeaders.USER_AGENT, "jutopia/0.1 (junbyounguk@gmail.com)")
                .build();
    }
}