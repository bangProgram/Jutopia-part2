package com.jbproject.jutopia.rest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post_reply_rl")
public class PostReplyRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;
    @Column(name = "reply_id")
    private Long ReplyId;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false, insertable = false)
    private PostEntity postEntity;
    @ManyToOne
    @JoinColumn(name = "reply_id", updatable = false, insertable = false)
    private ReplyEntity replyEntity;
}
