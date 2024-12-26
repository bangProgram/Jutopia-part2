package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.dto.model.XmlCorpModel;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpCisStatPayload;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpReportPayload;
import com.jbproject.jutopia.rest.dto.result.MergeResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface AdminUtilService {

    Optional<CorpCisEntity> findById(CorpCisKey key);
    void saveCorp(XmlCorpModel xmlCorpModel);
    void mergeCorpDetail(MergeCorpDetailPayload payload) throws Exception;
    MergeResult mergeCorpCis(MergeCorpReportPayload payload, MultipartFile file) throws Exception;
    MergeResult mergeCisStat(MergeCorpCisStatPayload payload);
}
