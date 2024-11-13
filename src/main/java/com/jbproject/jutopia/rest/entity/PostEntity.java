package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tb_post")
public class PostEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_type")
    private String postType;
    @Column(name = "post_title")
    private String postTitle;
    @Column(name = "post_detail")
    private String postDetail;
    @Column(name = "stock_code")
    private String stockCode;
    @Column(name = "post_writer")
    private String postWriter;
    @Column(name = "postViewrs")
    private Long postViewrs;


    @OneToMany(mappedBy = "postEntity")
    private List<PostReplyRelation> postReplyRelation;

    @Builder
    public PostEntity(
        String postType, String postTitle, String postDetail,
        String stockCode, String postWriter, Long postViewrs
    ){
        this.postType = postType;
        this.postTitle = postTitle;
        this.postDetail = postDetail;
        this.stockCode = stockCode;
        this.postWriter = postWriter;
        this.postViewrs = postViewrs;
    }

    public void updatePost(){}

}
