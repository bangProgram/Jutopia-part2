package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.CorpEntity;
import com.jbproject.jutopia.rest.repository.custom.CorpCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorpRepository extends JpaRepository<CorpEntity, String>, CorpCustom {

}
