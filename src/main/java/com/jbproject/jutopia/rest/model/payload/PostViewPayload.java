package com.jbproject.jutopia.rest.model.payload;

import lombok.Data;

@Data
public class PostViewPayload {

    private Long postId;

    private String postType;
    private String postTitle;
    private String postDetail;
    private String stockCode;
    private String stockName;
    private String postWriter;
    private String postWriterId;
}
