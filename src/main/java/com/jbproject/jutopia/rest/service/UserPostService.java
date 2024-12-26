package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.rest.dto.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.dto.payload.ViewPostPayload;
import com.jbproject.jutopia.rest.dto.payload.ReplyPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchReplyPayload;
import com.jbproject.jutopia.rest.dto.result.PostResult;
import com.jbproject.jutopia.rest.dto.result.ReplyResult;

import java.util.List;

public interface UserPostService {
    List<PostResult> searchPostList(SearchPostPayload payload);
    PostResult getPostDetail(Long postId);
    Long savePost(ViewPostPayload payload, AccessJwtToken.AccessJwtPrincipal principal);
    List<ReplyResult> searchReplyList(SearchReplyPayload payload);
    void savePostReply(ReplyPayload payload, AccessJwtToken.AccessJwtPrincipal principal);
}
