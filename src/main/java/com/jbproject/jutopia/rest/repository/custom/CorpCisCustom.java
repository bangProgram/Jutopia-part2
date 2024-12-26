package com.jbproject.jutopia.rest.repository.custom;


import com.jbproject.jutopia.rest.dto.model.CorpCisModel;
import com.jbproject.jutopia.rest.dto.model.CorpCisStatModel;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpCisStatPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchCorpPayload;

import java.util.List;

public interface CorpCisCustom {

    List<CorpCisModel> getCorpCisList(MergeCorpCisStatPayload payload);
    List<CorpCisModel> searchCorpCisList(SearchCorpPayload payload);
    List<CorpCisStatModel> getCorpCisStatList(MergeCorpCisStatPayload payload);
}
