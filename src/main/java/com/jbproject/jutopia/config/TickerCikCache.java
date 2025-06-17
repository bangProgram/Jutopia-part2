package com.jbproject.jutopia.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class TickerCikCache {

    private final WebClient webClient;
    private Map<String, String> cache = new ConcurrentHashMap<>();

    /** 매일 06:00 뉴욕시각에 업데이트 */
    @Scheduled(cron = "0 0 19 * * ?", zone = "America/New_York")
    public void refresh() throws IOException {
        String raw = webClient.get()
                .uri("https://www.sec.gov/include/ticker.txt")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Map<String, String> map = new HashMap<>();
        CSVParser.parse(raw, CSVFormat.DEFAULT.withDelimiter(' '))
                .forEach(r -> map.put(r.get(0).toUpperCase(), String.format("%010d",
                        Integer.parseInt(r.get(1)))));
        cache = map;
        log.info("✔︎ Ticker→CIK 매핑 {}건 로드 완료", map.size());
    }

    public String cikOf(String ticker) {
        return cache.get(ticker.toUpperCase());
    }
}