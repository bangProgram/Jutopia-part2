package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.repository.custom.RoleMenuCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMenuRepository extends JpaRepository<RoleMenuRelation, Long>, RoleMenuCustom {

    List<RoleMenuRelation> findAll();
}
