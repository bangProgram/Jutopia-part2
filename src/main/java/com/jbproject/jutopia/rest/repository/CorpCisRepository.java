package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.repository.custom.CorpCisCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpCisRepository extends JpaRepository<CorpCisEntity, CorpCisKey>, CorpCisCustom {
}
