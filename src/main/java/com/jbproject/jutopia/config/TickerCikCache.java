package com.jbproject.jutopia.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
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
    private static Map<String, String> tickerToCik = new ConcurrentHashMap<>();
    private static Map<String, String> cikToTicker = new ConcurrentHashMap<>();

    @PostConstruct  // 빈 생성 후 자동 실행
    public void init() throws IOException {
        refresh();  // 초기 데이터 로딩
    }

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
    public static String tickerToCik(String ticker) {return tickerToCik.get(ticker.toUpperCase(Locale.ROOT));}

    /** CIK → 티커  */
    public static String cikToTicker(String cikCode) {return cikToTicker.get(cikCode);}
}