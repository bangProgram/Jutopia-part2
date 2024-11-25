package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.rest.model.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.model.payload.ViewPostPayload;
import com.jbproject.jutopia.rest.model.payload.ReplyPayload;
import com.jbproject.jutopia.rest.model.payload.SearchReplyPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;
import com.jbproject.jutopia.rest.model.result.ReplyResult;

import java.util.List;

public interface UserPostService {
    List<PostResult> searchPostList(SearchPostPayload payload);
    PostResult getPostDetail(Long postId);
    Long savePost(ViewPostPayload payload, AccessJwtToken.AccessJwtPrincipal principal);
    List<ReplyResult> searchReplyList(SearchReplyPayload payload);
    void savePostReply(ReplyPayload payload, AccessJwtToken.AccessJwtPrincipal principal);
}
