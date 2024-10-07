package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.model.payload.MenuCudPayload;
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
@Table(name = "tb_menu")
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name", nullable = false)
    private String menuName;
    @Column(name = "menu_url", nullable = false)
    private String menuUrl;
    @Column(name = "use_yn", nullable = false)
    private String useYn;
    @Column(name = "seq")
    private int seq;
    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuEntity parentMenu;

    @OneToMany(mappedBy = "parentMenu")
    private List<MenuEntity> childMenu;

    @OneToMany(mappedBy = "menuEntity")
    private List<RoleMenuRelation> roleMenuRelations;

    @Builder
    public MenuEntity(String menuName,String menuUrl,String useYn,int seq,Long parentId){
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.useYn = useYn;
        this.seq = seq;
        this.parentId = parentId;
    }


    public void modMenu(MenuCudPayload payload){
        this.menuName = payload.getMenuName();
        this.menuUrl = payload.getMenuUrl();
        this.useYn = payload.getUseYn();
        this.seq = payload.getSeq();
        this.parentId = payload.getParentId();
    }
}
