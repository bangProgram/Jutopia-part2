package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.model.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;

import java.util.List;

public interface PostCustom {
    List<PostResult> searchPostList(SearchPostPayload payload);
}
