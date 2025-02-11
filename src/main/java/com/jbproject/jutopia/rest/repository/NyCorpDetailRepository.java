package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.dto.payload.SearchNyCorpPayload;
import com.jbproject.jutopia.rest.entity.NyCorpDetailEntity;
import com.jbproject.jutopia.rest.repository.custom.NyCorpDetailCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NyCorpDetailRepository extends JpaRepository<NyCorpDetailEntity, String>, NyCorpDetailCustom {

    List<NyCorpDetailEntity> findByStockCodeLikeOrStockNameLike(String searchWord1, String searchWord2);
}
