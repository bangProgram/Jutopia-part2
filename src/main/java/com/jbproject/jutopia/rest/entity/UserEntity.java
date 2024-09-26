package com.jbproject.jutopia.rest.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "tb_user",
        indexes = @Index(name = "idx_user", columnList = "user_id, email, phone")
)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private int age;
    @Column(name = "phone")
    private String phone;
    @Column(name = "profile_img")
    private String profileImg;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "refresh_token")
    private String refreshToken;



    @OneToMany(mappedBy = "userEntity")
    private List<UserSocialRelation> userSocialRelations;

    @Builder
    public UserEntity(
            String userId, String email,String name,String password,int age,
            String phone, String profileImg, String role,String refreshToken
    ){
        this.userId =userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.age = age;
        this.role = role;
        this.phone= phone;
        this.profileImg= profileImg;
        this.refreshToken = refreshToken;
    }
}
