package com.jbproject.jutopia.rest.dto.payload;

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
    private String delYn;
}
