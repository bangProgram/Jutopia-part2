package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.relation.RoleMenuRelation;
import com.jbproject.jutopia.rest.dto.payload.MenuCudPayload;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "menu_detail")
    private String menuDetail;
    @Column(name = "menu_url", nullable = false)
    private String menuUrl;
    @Column(name = "use_yn", nullable = false)
    private String useYn;
    @Column(name = "seq")
    private int seq;
    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;
    @Column(name = "menu_type")
    private String menuType;
    @Column(name = "is_show_bar")
    private String showYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuEntity parentMenu;

    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.REMOVE)
    private List<MenuEntity> childMenu;

    @OneToMany(mappedBy = "menuEntity", cascade = CascadeType.REMOVE)
    private List<RoleMenuRelation> roleMenuRelations;

    @Builder
    public MenuEntity(String menuName,String menuUrl,String useYn,int seq, String menuType, String menuDetail, String showYn){
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.useYn = useYn;
        this.seq = seq;
        this.menuType = menuType;
        this.menuDetail = menuDetail;
        this.showYn = showYn;
    }


    public void modMenu(MenuCudPayload payload){
        this.menuName = payload.getMenuName();
        this.menuUrl = payload.getMenuUrl();
        this.useYn = payload.getUseYn();
        this.seq = payload.getSeq();
        this.menuType = payload.getMenuType();
        this.menuDetail = payload.getMenuDetail();
        this.showYn = payload.getShowYn();
    }
}
