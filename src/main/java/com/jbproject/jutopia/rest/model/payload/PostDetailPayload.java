package com.jbproject.jutopia.rest.model.payload;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PostDetailPayload {

    private String postId;

    private String postType;
    private String postTitle;
    private String postDetail;
    private String stockCode;

}
