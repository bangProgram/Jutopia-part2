package com.jbproject.jutopia.rest.model;

import lombok.Builder;
import lombok.Data;

@Data
public class NetStatModel {

    private Long befNetAmount;
    private Long netAmount;

    @Builder
    public NetStatModel(Long befNetAmount, Long netAmount){
        this.befNetAmount = befNetAmount;
        this.netAmount = netAmount;
    }
}
