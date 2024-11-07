package com.jbproject.jutopia.rest.model.result;

import lombok.Data;

@Data
public class MergeResult {
    private int createCnt;
    private int updateCnt;
    private int deleteCnt;
}
