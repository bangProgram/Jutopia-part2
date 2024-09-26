package com.jbproject.jutopia.rest.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "tb_social",
        indexes = @Index(name = "idx_social", columnList = "social_id, social_type")
)
public class SocialEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_id", nullable = false)
    private String socialId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "social_type")
    private String socialType;

    @OneToMany(mappedBy = "socialEntity")
    private List<UserSocialRelation> userSocialRelations;

    @Builder
    public SocialEntity(
            String socialId, String email,String name,
            int age, String socialType
    ){
        this.socialId = socialId;
        this.email = email;
        this.name = name;
        this.age = age;
        this.socialType = socialType;
    }
}
