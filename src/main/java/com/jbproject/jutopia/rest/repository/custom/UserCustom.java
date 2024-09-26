package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.entity.UserEntity;

public interface UserCustom {

    UserEntity getUserInfo(String userId);
}
