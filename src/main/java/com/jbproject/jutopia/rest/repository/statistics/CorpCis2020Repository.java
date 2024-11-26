package com.jbproject.jutopia.rest.repository.statistics;

import com.jbproject.jutopia.rest.entity.CorpCisEntity;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.entity.statistics.CorpCis2020Entity;
import com.jbproject.jutopia.rest.repository.custom.CorpCisCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpCis2020Repository extends JpaRepository<CorpCis2020Entity, CorpCisKey> {

}
