package com.jbproject.jutopia.rest.repository.custom;


import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.model.CorpCisStatModel;
import com.jbproject.jutopia.rest.model.payload.MergeCorpCisStatPayload;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.result.CorpCisResult;
import com.jbproject.jutopia.rest.model.result.CorpResult;

import java.util.List;

public interface CorpCisCustom {

    List<CorpCisResult> getCorpCisList(MergeCorpCisStatPayload payload);
    List<CorpCisStatModel> getCorpCisStatList(MergeCorpCisStatPayload payload);
}
