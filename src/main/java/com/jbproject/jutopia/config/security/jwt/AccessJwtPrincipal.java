package com.jbproject.jutopia.config.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessJwtPrincipal {
     private String userEmail;
     private String userName;
     private int age;
     private String socialType;
     private String socialId;
     private String role;

     @Builder
     public AccessJwtPrincipal(
        String userEmail, String userName, int age,
        String socialType, String socialId, String role
     ){
         this.userEmail = userEmail;
         this.userName = userName;
         this.age = age;
         this.socialType = socialType;
         this.socialId = socialId;
         this.role = role;
     }
}
