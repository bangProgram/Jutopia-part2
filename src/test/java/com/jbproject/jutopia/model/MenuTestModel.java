package com.jbproject.jutopia.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jbproject.jutopia.rest.entity.MenuEntity;
import groovy.transform.builder.Builder;
import jakarta.persistence.Column;


public class MenuTestModel {


    @JsonProperty("id")
    private Long id;
    @JsonProperty("menuName")
    private String menuName;
    @JsonProperty("menuDetail")
    private String menuDetail;
    @JsonProperty("menuUrl")
    private String menuUrl;
    @JsonProperty("useYn")
    private String useYn;
    @JsonProperty("seq")
    private int seq;
    @JsonProperty("parentId")
    private Long parentId;
    @JsonProperty("menuType")
    private String menuType;
    @JsonProperty("showYn")
    private String showYn;

    public Long getId() {return this.id; }
    public String getMenuName() {return this.menuName; }
    public String getMenuDetail() {return this.menuDetail; }
    public String getMenuUrl() {return this.menuUrl; }
    public String getUseYn() {return this.useYn; }
    public int getSeq() {return this.seq; }
    public Long getParentId() {return this.parentId; }
    public String getMenuType() {return this.menuType; }
    public String getShowYn() {return this.showYn; }

    public MenuTestModel(){};

    public MenuTestModel(MenuEntity entity){
            this.id = entity.getId();
            this.menuName = entity.getMenuName();
            this.menuDetail = entity.getMenuDetail();
            this.menuUrl = entity.getMenuUrl();
            this.useYn = entity.getUseYn();
            this.seq = entity.getSeq();
            this.parentId = entity.getParentId();
            this.menuType = entity.getMenuType();
            this.showYn = entity.getShowYn();
    }
}
