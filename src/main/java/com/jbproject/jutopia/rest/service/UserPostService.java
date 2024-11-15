package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.rest.model.payload.PostSearchPayload;
import com.jbproject.jutopia.rest.model.payload.PostViewPayload;
import com.jbproject.jutopia.rest.model.payload.ReplySearchPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;
import com.jbproject.jutopia.rest.model.result.ReplyResult;

import java.util.List;

public interface UserPostService {
    List<PostResult> searchPostList(PostSearchPayload payload);
    PostResult getPostDetail(Long postId);
    Long savePost(PostViewPayload payload, AccessJwtToken.AccessJwtPrincipal principal);
    ReplyResult searchReplyList(ReplySearchPayload payload);
}
