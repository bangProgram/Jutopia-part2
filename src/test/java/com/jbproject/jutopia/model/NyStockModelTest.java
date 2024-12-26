package com.jbproject.jutopia.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.rest.dto.model.ApiResponseModel;
import com.jbproject.jutopia.rest.dto.model.CorpDetailModel;
import com.jbproject.jutopia.rest.dto.model.NyStockModel;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class NyStockModelTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void modelSerializeTest1() throws URISyntaxException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        URI request = new URI("https://api.stock.naver.com/stock/exchange/NASDAQ/marketValue?page=1&pageSize=10");

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(request, String.class);
        ApiResponseModel model1 = objectMapper.readValue(response, ApiResponseModel.class);
        System.out.println("model1 : "+model1);

    }

    @Test
    void modelSerializeTest2() throws URISyntaxException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        URL url = new URL("https://api.stock.naver.com/stock/exchange/NASDAQ/marketValue?page=1&pageSize=10");
        InputStreamReader isr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        ApiResponseModel model2 = objectMapper.readValue(isr, ApiResponseModel.class);

        System.out.println("model2 : "+model2);

    }

}
