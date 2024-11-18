package com.jbproject.jutopia.rest.model.result;

import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResult {

    private Long replyId;
    private String replyDetail;
    private String replyWriter;
    private Long parentId;
    private Long supperId;
    private int replyDepth;

    private List<ReplyResult> childReplyList;


    public static ReplyResult create(ReplyEntity entity){
        ReplyResult result = new ReplyResult();

        result.setReplyId(entity.getId());
        result.setReplyDetail(entity.getReplyDetail());
        result.setReplyWriter(entity.getReplyWriter());
        result.setParentId(entity.getParentId());
        result.setSupperId(entity.getSupperId());
        result.setReplyDepth(entity.getReplyDepth());

        return result;
    }
}
