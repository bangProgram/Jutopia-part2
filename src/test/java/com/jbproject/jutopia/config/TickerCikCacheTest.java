package com.jbproject.jutopia.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TickerCikCacheTest {

    @Test
    void tickerToCik() {
        String rst = TickerCikCache.tickerToCik("AAPL");
        log.info("tickerToCik rst : {}",rst);
    }

    @Test
    void cikToTicker() {
        String rst = TickerCikCache.cikToTicker("CIK0000320193");
        log.info("cikToTicker rst : {}",rst);
    }
}
