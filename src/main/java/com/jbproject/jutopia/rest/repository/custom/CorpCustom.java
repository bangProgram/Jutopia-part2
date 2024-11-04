package com.jbproject.jutopia.rest.repository.custom;


import com.jbproject.jutopia.rest.model.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.model.result.CorpResult;

import java.util.List;

public interface CorpCustom {
    List<CorpResult> getCorpListByMergeCorpDetailPayload(MergeCorpDetailPayload payload);
}
