package com.jbproject.jutopia.rest.repository.statistics;

import com.jbproject.jutopia.rest.entity.CorpCisStatTEntity;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatTKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpCisStat<T extends CorpCisStatTEntity> extends JpaRepository<T, CorpCisStatTKey> {
}
