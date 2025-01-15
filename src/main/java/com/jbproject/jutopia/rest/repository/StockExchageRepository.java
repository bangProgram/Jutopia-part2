package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.StockExchageEntity;
import com.jbproject.jutopia.rest.entity.key.StockExchangeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockExchageRepository extends JpaRepository<StockExchageEntity, StockExchangeKey> {
}
