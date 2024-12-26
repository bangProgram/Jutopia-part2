package com.jbproject.jutopia.rest.entity;

import com.jbproject.jutopia.rest.dto.CorpDetailModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_corp_detail")
public class CorpDetailEntity {

    @Id
    @Column(name = "corp_code")
    private String corpCode;
    @Column(name = "corp_name")
    private String corpName;
    @Column(name = "stock_name")
    private String stockName;
    @Column(name = "stock_code")
    private String stockCode;
    @Column(name = "ceo_name")
    private String ceoName;
    @Column(name = "corp_cls")
    private String corpCls;
    @Column(name = "jurir_no")
    private String jurirNo;
    @Column(name = "bizr_no")
    private String bizrNo;
    @Column(name = "address")
    private String address;
    @Column(name = "hm_url")
    private String hmUrl;
    @Column(name = "ir_url")
    private String irUrl;
    @Column(name = "phn_no")
    private String phnNo;
    @Column(name = "fax_no")
    private String faxNo;
    @Column(name = "induty_code")
    private String indutyCode;
    @Column(name = "est_date")
    private String estDate;
    @Column(name = "acc_mt")
    private String accMt;

    @OneToOne(fetch = FetchType.LAZY)
    private CorpEntity corpEntity;

    public CorpDetailEntity(CorpDetailModel corpDetailModel){
        this.corpCode = corpDetailModel.getCorpCode();
        this.corpName = corpDetailModel.getCorpName();
        this.stockName =  corpDetailModel.getStockName();
        this.stockCode =  corpDetailModel.getStockCode();
        this.ceoName =  corpDetailModel.getCeoNm();
        this.corpCls =  corpDetailModel.getCorpCls();
        this.jurirNo =  corpDetailModel.getJurirNo();
        this.bizrNo =  corpDetailModel.getBizrNo();
        this.address =  corpDetailModel.getAdres();
        this.hmUrl =  corpDetailModel.getHmUrl();
        this.irUrl =  corpDetailModel.getIrUrl();
        this.phnNo =  corpDetailModel.getPhnNo();
        this.faxNo =  corpDetailModel.getFaxNo();
        this.indutyCode =  corpDetailModel.getIndutyCode();
        this.estDate =  corpDetailModel.getEstDt();
        this.accMt =  corpDetailModel.getAccMt();
    }

    @Builder
    public CorpDetailEntity(
            String corpCode, String corpName, String stockName, String stockCode
            ,String ceoName ,String corpCls ,String jurirNo ,String bizrNo
            ,String address ,String hmUrl ,String irUrl ,String phnNo
            ,String faxNo ,String indutyCode ,String estDate ,String accMt
    ) {
        this.corpCode = corpCode;
        this.corpName = corpName;
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.ceoName = ceoName;
        this.corpCls = corpCls;
        this.jurirNo = jurirNo;
        this.bizrNo = bizrNo;
        this.address = address;
        this.hmUrl = hmUrl;
        this.irUrl = irUrl;
        this.phnNo = phnNo;
        this.faxNo = faxNo;
        this.indutyCode = indutyCode;
        this.estDate = estDate;
        this.accMt = accMt;
    }
}
