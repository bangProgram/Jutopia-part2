package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.entity.CorpDetailEntity;
import com.jbproject.jutopia.rest.entity.CorpEntity;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.CorpModel;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.result.AuthResult;
import com.jbproject.jutopia.rest.model.result.CorpResult;
import com.jbproject.jutopia.rest.repository.CorpDetailRepository;
import com.jbproject.jutopia.rest.repository.CorpRepository;
import com.jbproject.jutopia.rest.service.AdminUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminUtilServiceImpl implements AdminUtilService {

    private final CorpRepository corpRepository;

    private final CorpDetailRepository corpDetailRepository;

    public List<CorpResult> getCorpListByMergeCorpDetailPayload(MergeCorpDetailPayload payload){
        System.out.println("test3");
        return corpRepository.getCorpListByMergeCorpDetailPayload(payload);
    }
    public void saveCorp(XmlCorpModel xmlCorpModel){

        System.out.println("test 2 : "+xmlCorpModel.getCorpModels().getFirst());

        for(CorpModel corpModel : xmlCorpModel.getCorpModels()){
            System.out.println("test 3 : "+ corpModel);
            CorpEntity newCorp = CorpEntity.builder()
                    .corpCode(corpModel.getCorpCode())
                    .corpName(corpModel.getCorpName())
                    .stockCode(corpModel.getStockCode())
                    .modifyDate(LocalDate.parse(corpModel.getModifyDate(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                    .build();

            System.out.println("test 3 : "+ corpModel);
            corpRepository.save(newCorp);
        }
    }

    public void saveCorpDetail(CorpDetailModel corpDetailModel){

        CorpDetailEntity newCorpDetail = CorpDetailEntity.builder()
                .corpCode(corpDetailModel.getCorpCode())
                .corpName(corpDetailModel.getCorpName())
                .stockName(corpDetailModel.getStockName())
                .stockCode(corpDetailModel.getStockCode())
                .ceoName(corpDetailModel.getCeoNm())
                .corpCls(corpDetailModel.getCorpCls())
                .jurirNo(corpDetailModel.getJurirNo())
                .bizrNo(corpDetailModel.getBizrNo())
                .address(corpDetailModel.getAdres())
                .hmUrl(corpDetailModel.getHmUrl())
                .irUrl(corpDetailModel.getIrUrl())
                .phnNo(corpDetailModel.getPhnNo())
                .faxNo(corpDetailModel.getFaxNo())
                .indutyCode(corpDetailModel.getIndutyCode())
                .estDate(corpDetailModel.getEstDt())
                .accMt(corpDetailModel.getAccMt())
                .build();

        corpDetailRepository.save(newCorpDetail);
    }


}
