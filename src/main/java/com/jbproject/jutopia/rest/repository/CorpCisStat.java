package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.CorpCis;
import com.jbproject.jutopia.rest.entity.key.CorpCisKey;
import com.jbproject.jutopia.rest.repository.custom.CorpCisCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface CorpCisStat<T extends CorpCis> extends JpaRepository<T, CorpCisKey> {

}
