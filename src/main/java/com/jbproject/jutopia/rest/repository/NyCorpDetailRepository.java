package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;
import com.jbproject.jutopia.rest.repository.custom.NyCorpDetailCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NyCorpDetailRepository extends JpaRepository<NyCorpDetailEntity, String>, NyCorpDetailCustom {
}
