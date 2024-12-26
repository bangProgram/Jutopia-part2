package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.dto.CorpCisModel;
import com.jbproject.jutopia.rest.dto.payload.SearchCorpPayload;
import com.jbproject.jutopia.rest.dto.result.CorpCisResult;
import com.jbproject.jutopia.rest.repository.CorpCisRepository;
import com.jbproject.jutopia.rest.service.CorpService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CorpServiceImpl implements CorpService {

    private final CorpCisRepository corpCisRepository;

    public List<CorpCisResult> searchCorpList(SearchCorpPayload payload) {
        return new ArrayList<>();
    }

    private List<String> getStockCodeList(SearchCorpPayload payload){
        List<CorpCisModel> resultList = corpCisRepository.searchCorpCisList(payload);

        return new ArrayList<>();
    }
}
