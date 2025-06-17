package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.StockIndustryEntity;
import com.jbproject.jutopia.rest.repository.custom.StockIndustryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockIndustryRepository extends JpaRepository<StockIndustryEntity, String>, StockIndustryCustom {
}
