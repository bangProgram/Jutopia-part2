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
    @Column(name = "age")
    private int age;
    @Column(name = "role", nullable = false)
    private String role;

    @Builder
    public UserEntity(String emil, String name, int age, String role){
        this.email = emil;
        this.name = name;
        this.age = age;
        this.role = role;
    }
}
