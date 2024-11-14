package com.jbproject.jutopia.rest.model.result;

import com.jbproject.jutopia.rest.entity.PostEntity;
import com.jbproject.jutopia.rest.entity.ReplyEntity;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ReplyResult {

    private Long replyId;
    private String replyDetail;
    private String replyWriter;

    public static ReplyResult create(ReplyEntity entity){
        ReplyResult result = new ReplyResult();

        result.setReplyId(entity.getId()); ;
        result.setReplyDetail(entity.getReplyDetail());
        result.setReplyWriter(entity.getReplyWriter());

        return result;
    }
}
