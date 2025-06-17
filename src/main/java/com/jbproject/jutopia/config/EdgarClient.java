package com.jbproject.jutopia.config;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class EdgarClient {

    private final WebClient edgarWebClient;

    public JsonNode getSubmissions(String cik) {
        return edgarWebClient.get()
                .uri("/submissions/CIK{cik}.json", cik)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    public JsonNode getCompanyFacts(String cik) {
        return edgarWebClient.get()
                .uri("/api/xbrl/companyfacts/{cik}.json", cik)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}