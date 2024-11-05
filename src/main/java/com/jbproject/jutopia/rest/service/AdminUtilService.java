package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.result.CorpResult;

import java.util.List;


public interface AdminUtilService {

    List<CorpResult> getCorpListByMergeCorpDetailPayload(MergeCorpDetailPayload payload);
    void saveCorp(XmlCorpModel xmlCorpModel);
    void saveCorpDetail(CorpDetailModel corpDetailModel);
}
