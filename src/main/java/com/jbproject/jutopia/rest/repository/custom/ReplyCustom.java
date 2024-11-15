package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.model.result.ReplyResult;

import java.util.List;

public interface ReplyCustom {
    List<ReplyResult> getReplyListBySupperId(Long supperId);
}
