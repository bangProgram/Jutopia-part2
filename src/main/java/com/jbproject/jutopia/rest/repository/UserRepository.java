package com.jbproject.jutopia.rest.repository;

import com.jbproject.jutopia.rest.entity.UserEntity;
import com.jbproject.jutopia.rest.repository.custom.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserCustom {
    UserEntity findByUserId(String userId);
}
