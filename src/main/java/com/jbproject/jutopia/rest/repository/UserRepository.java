package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.repository.custom.AuthCustom;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>, AuthCustom {

}
