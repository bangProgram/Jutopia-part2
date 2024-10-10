package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.CommCodeEntity;
import com.jbproject.jutopia.rest.entity.key.CommCodeKey;
import com.jbproject.jutopia.rest.repository.custom.CommCodeCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommCodeRepository extends JpaRepository<CommCodeEntity, CommCodeKey>, CommCodeCustom {
}
