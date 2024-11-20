package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.CorpCisStatEntity;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatKey;
import com.jbproject.jutopia.rest.repository.custom.CorpCisCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpCisStatRepository extends JpaRepository<CorpCisStatEntity, CorpCisStatKey> {

}
