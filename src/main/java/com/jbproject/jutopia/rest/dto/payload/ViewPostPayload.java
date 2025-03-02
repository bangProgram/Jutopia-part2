package com.jbproject.jutopia.rest.dto.payload;

import lombok.Data;

@Data
public class ViewPostPayload {

    private Long postId;

    private String postType;
    private String postTitle;
    private String postDetail;
    private String stockCode;
    private String stockName;
    private String postWriter;
    private String postWriterId;
}
