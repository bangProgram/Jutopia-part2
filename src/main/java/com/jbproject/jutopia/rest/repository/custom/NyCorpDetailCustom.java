package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.dto.payload.SearchNyCorpPayload;
import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;

import java.util.List;

public interface NyCorpDetailCustom {
    List<NyCorpDetailEntity> searchNyCorpList(SearchNyCorpPayload payload);
}
