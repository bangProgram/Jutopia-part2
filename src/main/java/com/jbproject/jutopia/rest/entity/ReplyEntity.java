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
