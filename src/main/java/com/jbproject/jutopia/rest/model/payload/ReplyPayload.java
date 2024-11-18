package com.jbproject.jutopia.rest.model.payload;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ReplyPayload {

    private Long postId;
    private Long replyId;
    private String replyDetail;
    private Long parentId;
    private Long supperId;
    private int replyDepth = 0;
    private String replyWriter;
    private String replyWriterId;
}
