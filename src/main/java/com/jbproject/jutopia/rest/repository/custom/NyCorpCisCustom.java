package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.entity.NyCorpCisEntity;
import com.jbproject.jutopia.rest.entity.key.NyCorpCisKey;

import java.util.List;

public interface NyCorpCisCustom {

    List<NyCorpCisKey> getNyCorpCisKey(String cikCode);
}
