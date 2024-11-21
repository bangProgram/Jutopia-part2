package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.CorpDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorpDetailRepository extends JpaRepository<CorpDetailEntity, String> {
    List<CorpDetailEntity> findByStockNameLike(String stockName);
    List<CorpDetailEntity> findByCorpClsIn(List<String> corpCls);
}
