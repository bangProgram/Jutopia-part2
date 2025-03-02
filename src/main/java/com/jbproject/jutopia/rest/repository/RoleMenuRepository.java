package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.relation.RoleMenuRelation;
import com.jbproject.jutopia.rest.repository.custom.RoleMenuCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMenuRepository extends JpaRepository<RoleMenuRelation, Long>, RoleMenuCustom {

    List<RoleMenuRelation> findAll();

    List<RoleMenuRelation> findByRoleId(String roleId);
}
