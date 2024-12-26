package com.jbproject.jutopia.rest.dto.result;

import com.jbproject.jutopia.rest.entity.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private String delYn;

    private List<ReplyResult> childReplyList = new ArrayList<>();


    public ReplyResult (ReplyEntity entity) {
        this.replyId = entity.getId();
        this.replyDetail = entity.getReplyDetail();
        this.replyWriter = entity.getReplyWriter();
        this.parentId = entity.getParentId();
        this.supperId = entity.getSupperId();
        this.replyDepth = entity.getReplyDepth();
        this.delYn = entity.getDelYn();

        this.childReplyList = entity.getChildReplyList().stream().map(ReplyResult::create).toList();
    }


    public static ReplyResult create(ReplyEntity entity){
        ReplyResult result = new ReplyResult();

        result.setReplyId(entity.getId());
        result.setReplyDetail(entity.getReplyDetail());
        result.setReplyWriter(entity.getReplyWriter());
        result.setParentId(entity.getParentId());
        result.setSupperId(entity.getSupperId());
        result.setReplyDepth(entity.getReplyDepth());
        result.setDelYn(entity.getDelYn());

        return result;
    }
}
