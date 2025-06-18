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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class TickerCikCache {

    private final WebClient webClient;
    private Map<String, String> tickerToCik = new ConcurrentHashMap<>();
    private Map<String, String> cikToTicker = new ConcurrentHashMap<>();


    /** 하루 1회 새벽(서버 tz 04:00) 자동 갱신 */
    @Scheduled(cron = "0 0 4 * * ?")
    public void refresh() throws IOException {
        String raw = webClient.get()
                .uri("https://www.sec.gov/include/ticker.txt")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Map<String, String> t2c = new HashMap<>();
        Map<String, String> c2t = new HashMap<>();

        // 파일 형식:  aapl  320193   (티커, 공백, CIK)
        for (String line : raw.split("\n")) {
            String[] arr = line.split("\\s+");
            if (arr.length != 2) continue;
            String ticker = arr[0].toUpperCase(Locale.ROOT);
            String cik    = String.format("%010d", Integer.parseInt(arr[1]));
            t2c.put(ticker, cik);
            c2t.put(cik, ticker);
        }
        tickerToCik = t2c;
        cikToTicker = c2t;
        log.info("✔  ticker.txt loaded: {} pairs", t2c.size());
    }

    /** 티커 → CIK */
    public String cikOf(String ticker) {return tickerToCik.get(ticker.toUpperCase(Locale.ROOT));}

    /** CIK → 티커  (여러 티커가 있을 경우 첫 번째 반환) */
    public String tickerOf(String cik) {return cikToTicker.get(cik);}
}