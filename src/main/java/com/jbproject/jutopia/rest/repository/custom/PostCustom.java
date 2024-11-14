package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.model.payload.PostSearchPayload;
import com.jbproject.jutopia.rest.model.payload.PostViewPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;

import java.util.List;

public interface PostCustom {
    List<PostResult> searchPostList(PostSearchPayload payload);
}
