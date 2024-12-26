package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.entity.relation.PostReplyRelation;
import com.jbproject.jutopia.rest.dto.payload.ReplyPayload;
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
    @Column(name = "reply_writer", columnDefinition = "varchar(100)")
    private String replyWriter;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "supper_id")
    private Long supperId;
    @Column(name = "reply_depth")
    private int replyDepth;
    @Column(name = "del_yn", columnDefinition = "varchar(10)")
    private String delYn;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private ReplyEntity parentReply;

    @OneToMany(mappedBy = "parentReply")
    private List<ReplyEntity> childReplyList;

    @OneToMany(mappedBy = "replyEntity")
    private List<PostReplyRelation> postReplyRelation;

    @Builder
    public ReplyEntity(
            String replyDetail, String replyWriter
            , Long parentId, Long supperId, int replyDepth
            ,String createId, String updateId
    ){
        this.replyDetail = replyDetail;
        this.replyWriter = replyWriter;
        this.parentId = parentId;
        this.supperId = supperId;
        this.replyDepth = replyDepth;
        this.setCreateId(createId);
        this.setUpdateId(updateId);
    }

    public void updateReply(ReplyPayload payload){
        this.replyDetail = payload.getReplyDetail();
        this.replyWriter = payload.getReplyWriter();
        this.setUpdateId(payload.getReplyWriterId());
    }

}
