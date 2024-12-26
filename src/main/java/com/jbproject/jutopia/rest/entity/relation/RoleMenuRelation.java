package com.jbproject.jutopia.rest.entity.relation;

import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.rest.entity.MenuEntity;
import com.jbproject.jutopia.rest.entity.RoleEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "role_menu_rl",
        uniqueConstraints = {
                @UniqueConstraint(
                    name="relate_unique",
                    columnNames={"role_id","menu_id"}
                )
            },
        indexes = @Index(
                name = "idx_roleId",
                columnList = "role_id"
            )
        )
public class RoleMenuRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "role_id")
    private String roleId;
    @Column(name = "menu_id")
    private Long menuId;
    @Column(name = "isCud")
    private String isCud;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private RoleEntity roleEntity;
    @ManyToOne
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    private MenuEntity menuEntity;

    @Builder
    public RoleMenuRelation(String roleId, Long menuId, String isCud){
        this.roleId = roleId;
        this.menuId = menuId;
        this.isCud = isCud;
    }
}
