package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.MenuEntity;
import com.jbproject.jutopia.rest.repository.custom.MenuCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long>, MenuCustom {

}
