package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.entity.CorpDetailEntity;
import com.jbproject.jutopia.rest.dto.model.CorpDetailModel;
import com.jbproject.jutopia.rest.repository.CorpDetailRepository;
import com.jbproject.jutopia.rest.service.UtilService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UtilServiceImpl implements UtilService {

    private final CorpDetailRepository corpDetailRepository;

    public List<CorpDetailModel> getCorpDetailList(String stockName){
        List<CorpDetailEntity> corpDetailEntityList = corpDetailRepository.findByStockNameLike(stockName);

        if(corpDetailEntityList != null ){
            return corpDetailEntityList.stream().map(CorpDetailModel::create).toList();
        }else{
            return new ArrayList<>();
        }
    }
}
