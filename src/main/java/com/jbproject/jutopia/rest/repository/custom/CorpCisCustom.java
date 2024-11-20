package com.jbproject.jutopia.rest.repository.custom;


import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.result.CorpCisResult;
import com.jbproject.jutopia.rest.model.result.CorpResult;

import java.util.List;

public interface CorpCisCustom {

    List<CorpCisEntity> getAll1();
    List<CorpCisResult> getAll();
    List<CorpCisEntity> getByAccountIds1(List<String> accountIds);

    List<CorpCisResult> getByAccountIds2(List<String> accountIds);
}
