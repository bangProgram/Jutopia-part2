package com.jbproject.jutopia.rest.model.result;

import com.jbproject.jutopia.rest.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResult {

    private Long postId;

    private String postType;
    private String postTitle;
    private String postDetail;
    private String stockCode;
    private String stockName;
    private String postWriter;
    private Long postViewrs;
    private LocalDateTime createDttm;
    private List<ReplyResult> replyResults;

    public static PostResult create(PostEntity entity){
        PostResult result = new PostResult();

        result.setPostId(entity.getId()) ;
        result.setPostType(entity.getPostType());
        result.setPostTitle(entity.getPostTitle());
        result.setPostDetail(entity.getPostDetail());
        result.setStockCode(entity.getStockCode());
        result.setPostWriter(entity.getPostWriter());
        result.setPostViewrs(entity.getPostViewrs());

        return result;
    }
}
