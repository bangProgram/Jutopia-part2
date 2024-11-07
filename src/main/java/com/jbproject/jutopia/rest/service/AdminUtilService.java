package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.model.CorpCisModel;
import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.payload.MergeCorpReportPayload;
import com.jbproject.jutopia.rest.model.result.CorpResult;
import com.jbproject.jutopia.rest.model.result.MergeResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface AdminUtilService {

    List<CorpResult> getCorpListByMergeCorpDetailPayload(MergeCorpDetailPayload payload);
    Optional<CorpCisEntity> findById(CorpCisKey key);
    void saveCorp(XmlCorpModel xmlCorpModel);
    void saveCorpDetail(CorpDetailModel corpDetailModel);
    MergeResult mergeCorpCis(MergeCorpReportPayload payload, MultipartFile file) throws Exception;
}
