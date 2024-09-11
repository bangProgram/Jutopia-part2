package com.jbproject.jutopia.rest.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private int age;
    @Column(name = "social_type")
    private String socialType;
    @Column(name = "social_id")
    private String socialId;
    @Column(name = "role", nullable = false)
    private String role;

    @Builder
    public UserEntity(
            String email,String name,String password,int age,String socialType,
            String socialId,String role
    ){
        this.email = email;
        this.name = name;
        this.password = password;
        this.age = age;
        this.socialType = socialType;
        this.socialId = socialId;
        this.role = role;
    }
}
