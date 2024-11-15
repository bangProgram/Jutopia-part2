package com.jbproject.jutopia.rest.model.payload;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ReplyPayload {

    private Long replyId;
    private String replyDetail;
    private String replyWriter;
    private Long parentId = 0L;
    private Long supperId;
    private int replyDepth = 0;
}
