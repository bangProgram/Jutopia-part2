package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.NyCorpEntity;
import com.jbproject.jutopia.rest.repository.custom.NyCorpCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NyCorpRepository extends JpaRepository<NyCorpEntity, String>, NyCorpCustom {
}
