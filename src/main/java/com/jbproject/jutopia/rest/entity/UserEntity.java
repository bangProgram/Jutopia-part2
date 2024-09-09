package com.jbproject.jutopia.rest.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    private Long id;
    private String email;
    private String nickName;
    private Long age;
    private String role;
}
