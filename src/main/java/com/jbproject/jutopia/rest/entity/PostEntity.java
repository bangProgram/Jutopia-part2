package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.relation.PostReplyRelation;
import com.jbproject.jutopia.rest.dto.payload.ViewPostPayload;
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
    @Column(name = "stock_name")
    private String stockName;
    @Column(name = "post_writer")
    private String postWriter;
    @Column(name = "postViewrs")
    private Long postViewrs;


    @OneToMany(mappedBy = "postEntity")
    private List<PostReplyRelation> postReplyRelation;

    @Builder
    public PostEntity(
        String postType, String postTitle, String postDetail,
        String stockCode, String stockName, String postWriter, Long postViewrs
        ,String createId, String updateId
    ){
        this.postType = postType;
        this.postTitle = postTitle;
        this.postDetail = postDetail;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.postWriter = postWriter;
        this.postViewrs = postViewrs;
        this.setCreateId(createId);
        this.setUpdateId(updateId);
    }

    public void updatePost(ViewPostPayload payload){
        this.postType = payload.getPostType();
        this.postTitle = payload.getPostTitle();
        this.postDetail = payload.getPostDetail();
        this.stockCode = payload.getStockCode();
        this.stockName = payload.getStockName();
        this.setUpdateId(payload.getPostWriterId());
    }

}
