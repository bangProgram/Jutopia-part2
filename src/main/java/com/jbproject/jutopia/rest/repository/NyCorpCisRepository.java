package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.NyCorpCisEntity;
import com.jbproject.jutopia.rest.entity.key.NyCorpCisKey;
import com.jbproject.jutopia.rest.repository.custom.NyCorpCisCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NyCorpCisRepository extends JpaRepository<NyCorpCisEntity, NyCorpCisKey>, NyCorpCisCustom {
}
