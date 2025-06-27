package com.jbproject.jutopia.rest.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.config.EdgarClient;
import com.jbproject.jutopia.config.TickerCikCache;
import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;
import com.jbproject.jutopia.rest.entity.NyCorpEntity;
import com.jbproject.jutopia.rest.repository.NyCorpDetailRepository;
import com.jbproject.jutopia.rest.repository.NyCorpRepository;
import com.jbproject.jutopia.rest.repository.StockIndustryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Enumeration;

@Service
@RequiredArgsConstructor
@Transactional
public class NyCorpSyncServiceImpl {

    private final TickerCikCache tickerCikCache; // ticker.txt ↔ CIK
    private final EdgarClient edgarClient;

    private final NyCorpRepository nyCorpRepo;
    private final NyCorpDetailRepository nyCorpDetailRepo;
    private final StockIndustryRepository stockIndustryRepo;


    private final ObjectMapper objectMapper;

    /** 단일 티커 동기화 */
    public void sync(String ticker) {
        String cik = tickerCikCache.tickerToCik(ticker);
        if (cik == null) throw new IllegalArgumentException("Unknown ticker: " + ticker);

        /* ---------- 1) 기업 메타 ---------- */
        JsonNode sub = edgarClient.getSubmissions(cik);
        String companyName = sub.at("/entityName").asText();

        /* ---------- 2) 상세 정보 ---------- */
        NyCorpDetailEntity detail = nyCorpDetailRepo.findById(cik)
                .orElse(new NyCorpDetailEntity());
        detail.setNationType("US");

        // SIC 산업코드 → StockIndustryEntity 로 전파
        String sic = sub.at("/sic").asText(null);
        nyCorpDetailRepo.save(detail);

        /* ---------- 3) XBRL 재무 Facts ---------- */
        JsonNode facts = edgarClient.getCompanyFacts(cik).path("facts").path("us-gaap");
        storeFact(facts, cik, "Revenues", "USD");
        storeFact(facts, cik, "NetIncomeLoss", "USD");
        storeFact(facts, cik, "Assets", "USD");
    }

    private void storeFact(JsonNode usGaap, String cik, String concept, String unit) {
        JsonNode arr = usGaap.path(concept).path("units").path(unit);
        if (!arr.isArray()) return;
        for (JsonNode f : arr) {
            LocalDate end = LocalDate.parse(f.path("end").asText());
            BigDecimal val = f.path("val").decimalValue();

            // 예시: 각각을 NyFinancialFactEntity 로 저장
//            if (!financialFactRepo.existsByReutersCodeAndConceptAndEndDate(cik, concept, end)) {
//                financialFactRepo.save(NyFinancialFactEntity.of(cik, concept, unit, end, val));
//            }
        }
    }

    public void ingest(Path zipFile, int fromYear, int toYear) {
        try (ZipFile z = new ZipFile(zipFile.toFile())) {
            Enumeration<ZipArchiveEntry> en = z.getEntries();
            while (en.hasMoreElements()) {
                ZipArchiveEntry e = en.nextElement();
                if (!e.getName().endsWith(".json")) continue;

                JsonNode root = objectMapper.readTree(z.getInputStream(e));
                String cik = root.path("cik").asText();
                String ticker = tickerCikCache.tickerToCik(cik);
                String name   = root.path("entityName").asText();

                /* ---------- Corp ---------- */
                NyCorpEntity corp = nyCorpRepo.findById(cik)
                        .orElse(new NyCorpEntity());
//                corp.set(cik);
//                corp.setTicker(ticker);
//                corp.setCompanyName(name);
//                corp.setLastModified(LocalDate.now());
//                corpRepo.save(corp);

                /* ---------- Facts ---------- */
                JsonNode usGaap = root.path("facts").path("us-gaap");
                usGaap.fields().forEachRemaining(entry -> {
                    String concept = entry.getKey();
                    JsonNode usdArr = entry.getValue()
                            .path("units")
                            .path("USD");
                    if (!usdArr.isArray()) return;

                    usdArr.forEach(n -> {
                        int fy = n.path("fy").asInt();
                        String fp = n.path("fp").asText();      // Q1~Q4/FY
                        if (fy < fromYear || fy > toYear) return;

//                        UsFactKey key = new UsFactKey();
//                        key.setCik(cik);
//                        key.setFiscalYear(fy);
//                        key.setFiscalPeriod(fp);
//                        key.setConcept(concept);
//
//                        if (factRepo.existsById(key)) return;
//
//                        factRepo.save(new UsFinancialFactEntity(
//                                key,
//                                LocalDate.parse(n.path("end").asText()),
//                                n.path("val").decimalValue(),
//                                entry.getValue().path("label").asText(),
//                                n.path("form").asText()));
                    });
                });

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int cnt = br.read();

                String test = "1234";



            }
        } catch (IOException ex) {
            throw new RuntimeException("ingest failed", ex);
        }
    }
}
