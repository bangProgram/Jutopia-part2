package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.config.security.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_role_menu", uniqueConstraints = {
        @UniqueConstraint(
                name="relate_unique",
                columnNames={"role_id","menu_id"}
        )})
public class RoleMenuRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "role_id", insertable = false, updatable = false)
    private String roleId;
    @Column(name = "url",  insertable = false, updatable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menuEntity;
}
