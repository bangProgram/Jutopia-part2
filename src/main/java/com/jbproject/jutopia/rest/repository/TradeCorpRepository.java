package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.TradeCorpEntity;
import com.jbproject.jutopia.rest.repository.custom.TradeCorpCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeCorpRepository extends JpaRepository<TradeCorpEntity, Long>, TradeCorpCustom {
}
