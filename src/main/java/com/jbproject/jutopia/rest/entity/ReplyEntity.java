package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_reply")
public class ReplyEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "reply_detail")
    private String replyDetail;
    @Column(name = "reply_writer")
    private String replyWriter;
    @Column(name = "supper_id")
    private Long supperId;
    @Column(name = "reply_depth")
    private int replyDepth;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ReplyEntity parentReply;

    @OneToMany(mappedBy = "parentReply")
    private List<ReplyEntity> childReplyList;

    @OneToMany(mappedBy = "replyEntity")
    private List<PostReplyRelation> postReplyRelation;

    @Builder
    public ReplyEntity(
            String replyDetail, String replyWriter
    ){
        this.replyDetail = replyDetail;
        this.replyWriter = replyWriter;
    }

    public void updateReply(){}

}
