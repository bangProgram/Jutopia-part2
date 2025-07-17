package com.jbproject.jutopia.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.rest.dto.model.NyCorpCisModel;
import com.jbproject.jutopia.rest.entity.key.NyCorpCisKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EdgarClient {

    private final WebClient edgarWebClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 계정 ID → fallback 순위 목록
    private static final Map<String, List<String>> fallbackMap = Map.of(
            "Revenue", List.of("RevenueFromContractWithCustomerExcludingAssessedTax","SalesRevenueNet","Revenues"),
            "OperatingIncomeLoss", List.of("OperatingIncomeLoss","IncomeLossFromContinuingOperationsBeforeIncomeTaxesExtraordinaryItemsNoncontrollingInterest"),
            "DiscontinuedOperatingIncomeLoss", List.of("DisposalGroupIncludingDiscontinuedOperationOperatingIncomeLoss"),
            "ProfitLoss", List.of("ProfitLoss", "NetIncomeLoss","NetIncomeLossAvailableToCommonStockholdersBasic"),
            "BasicEarningsLossPerShare", List.of("EarningsPerShareBasic","EarningsPerShareDiluted")
    );

    public JsonNode getSubmissions(String cik) {
        return edgarWebClient.get()
                .uri("/submissions/CIK{cik}.json", cik)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    public String getCompanyFacts(String cik) {
        return edgarWebClient.get()
                .uri("/api/xbrl/companyfacts/CIK"+cik+".json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<NyCorpCisModel> parseCompanyFacts(String cikCode, JsonNode root) {
//        System.out.println("parseCompanyFacts : "+root);

        JsonNode facts = root.path("facts").path("us-gaap");

        List<NyCorpCisModel> results = new ArrayList<>();

        // Set으로 변환해서 빠르게 존재 여부 확인
        Set<String> existingKeySet = new HashSet<>();

        String errorModel = "";

        try {
            for (Map.Entry<String, List<String>> entry : fallbackMap.entrySet()) {
                String accountId = entry.getKey();
                for (String tag : entry.getValue()) {
                    JsonNode unitNode = facts.path(tag).path("units");
                    if (!unitNode.isMissingNode()) {
                        for (Iterator<String> it = unitNode.fieldNames(); it.hasNext(); ) {
                            String currency = it.next(); // 보통 USD or USD/shares
                            System.out.println("test111 : "+currency);
                            for (JsonNode fact : unitNode.path(currency)) {
                                String frame = fact.path("frame").asText("");
                                System.out.println("test222 : "+frame);
                                if (!frame.matches("CY\\d{4}Q[1-4]")) continue; // 분기 단독 실적만

                                String bsnsYear = frame.substring(2,6);
                                String quarterlyReportCode = frame.substring(frame.length() - 2); // Q1, Q2 등

                                // 존재 여부 확인
                                String key = String.join(":", cikCode, bsnsYear, quarterlyReportCode, accountId);
                                System.out.println("key : "+key + " / existingKeySet.contains(key) : "+existingKeySet.contains(key));
                                if (existingKeySet.contains(key)) continue;
                                existingKeySet.add(key);

                                NyCorpCisModel dto = new NyCorpCisModel();
                                dto.setCikCode(cikCode);
                                dto.setBsnsYear(bsnsYear);
                                dto.setQuarterlyReportCode(quarterlyReportCode); // "Q1", "Q2" 등
                                dto.setAccountId(accountId);
                                dto.setQuarterlyReportName(quarterlyReportCode+"분기 보고서");
                                dto.setClosingDate(LocalDate.parse(fact.path("end").asText()));
                                dto.setAccountName(tag); // 실제 태그명을 저장
                                dto.setNetAmount(fact.path("val").asText());
                                dto.setCurrency(currency);

                                results.add(dto);

                                errorModel = objectMapper.writeValueAsString(dto);
                            }
                        }
                        System.out.println("tag : "+tag);
                    }
                }
            }
        }catch (Exception e){
            System.out.println(" e : "+ e);
            System.out.println("errorModel : "+errorModel);
        }

        return results;
    }


    public List<NyCorpCisModel> parseCompanyFactsTest(JsonNode root) throws IOException {
        String cik = root.path("cik").asText(); // 또는 path에서 추출
        String cikCode = String.format("%010d", Integer.parseInt(cik));
        JsonNode facts = root.path("facts").path("us-gaap");

        List<NyCorpCisModel> results = new ArrayList<>();

        Map<String, Map<String, JsonNode>> dataMap = new HashMap<>();
        // Key: accountId
        // Value: Map<frame, fact>

        for (Map.Entry<String, List<String>> entry : fallbackMap.entrySet()) {
            String accountId = entry.getKey();
            for (String tag : entry.getValue()) {
                JsonNode unitNode = facts.path(tag).path("units");
                if (!unitNode.isMissingNode()) {
                    for (Iterator<String> it = unitNode.fieldNames(); it.hasNext(); ) {
                        String currency = it.next();
                        for (JsonNode fact : unitNode.path(currency)) {
                            String frame = fact.path("frame").asText("");
                            if (!frame.matches("CY\\d{4}Q[1-4]")) continue;
                            dataMap
                                    .computeIfAbsent(accountId, k -> new HashMap<>())
                                    .put(frame, fact);
                        }
                    }
                    break;
                }
            }
        }

        for (Map.Entry<String, Map<String, JsonNode>> accountEntry : dataMap.entrySet()) {
            String accountId = accountEntry.getKey();
            Map<String, JsonNode> frameToFact = accountEntry.getValue();

            for (Map.Entry<String, JsonNode> entry : frameToFact.entrySet()) {
                String frame = entry.getKey(); // e.g. CY2024Q2
                JsonNode fact = entry.getValue();

                NyCorpCisModel dto = new NyCorpCisModel();
                dto.setCikCode(cikCode);
                dto.setBsnsYear(String.valueOf(fact.path("fy").asInt()));
                dto.setQuarterlyReportCode(frame.substring(frame.length()-2));
                dto.setAccountId(accountId);
                dto.setQuarterlyReportName(fact.path("form").asText());
                dto.setClosingDate(LocalDate.parse(fact.path("end").asText()));
                dto.setAccountName(accountId); // tag 대신 accountId로 사용
                dto.setNetAmount(fact.path("val").asText());
                dto.setCurrency(getCurrencySafely(fact));

                // 🔥 이전 분기 계산
                String befFrame = getPreviousFrame(frame); // CY2024Q1
                JsonNode befFact = frameToFact.get(befFrame);
                if (befFact != null) {
                    dto.setBefNetAmount(befFact.path("val").asText());
                }

                results.add(dto);
            }
        }

        return results;
    }

    // 이전 년도 분기 체크
    private String getPreviousFrame(String frame) {
        // e.g., "CY2024Q2" → "CY2024Q1"
        int year = Integer.parseInt(frame.substring(2, 6));
        int quarter = Integer.parseInt(frame.substring(7));

        if (quarter == 1) {
            year -= 1;
            quarter = 4;
        } else {
            quarter -= 1;
        }
        return String.format("CY%04dQ%d", year, quarter);
    }

    // 통화코드 정보 방어코드
    private String getCurrencySafely(JsonNode fact) {
        // currency 정보가 없을 수 있으므로 예외 방지
        JsonNode parent = fact.get("parent");
        if (parent != null && parent.has("currency")) {
            return parent.get("currency").asText();
        }
        return "USD";
    }
}