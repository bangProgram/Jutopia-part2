package com.jbproject.jutopia.rest.repository.custom;


import com.jbproject.jutopia.rest.dto.payload.MergeCorpDetailPayload;
import com.jbproject.jutopia.rest.dto.result.CorpResult;

import java.util.List;

public interface CorpCustom {
    List<CorpResult> getCorpListByMergeCorpDetailPayload(MergeCorpDetailPayload payload);
}
