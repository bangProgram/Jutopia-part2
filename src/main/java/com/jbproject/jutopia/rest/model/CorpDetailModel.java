package com.jbproject.jutopia.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;


@JsonRootName("result")
@JsonIgnoreProperties({"status","message"})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class CorpDetailModel {
    /*
        corp_code 기업코드
        corp_name	정식명칭	정식회사명칭
        corp_name_eng	영문명칭	영문정식회사명칭
        stock_name	종목명(상장사) 또는 약식명칭(기타법인)	종목명(상장사) 또는 약식명칭(기타법인)
        stock_code	상장회사인 경우 주식의 종목코드	상장회사의 종목코드(6자리)
        ceo_nm	대표자명	대표자명
        corp_cls	법인구분	법인구분 : Y(유가), K(코스닥), N(코넥스), E(기타)
        jurir_no	법인등록번호	법인등록번호
        bizr_no	사업자등록번호	사업자등록번호
        adres	주소	주소
        hm_url	홈페이지	홈페이지
        ir_url	IR홈페이지	IR홈페이지
        phn_no	전화번호	전화번호
        fax_no	팩스번호	팩스번호
        induty_code	업종코드	업종코드
        est_dt	설립일(YYYYMMDD)	설립일(YYYYMMDD)
        acc_mt	결산월(MM)	결산월(MM)
     */

        @JsonProperty("corp_code")
        private String corpCode;
        @JsonProperty("corp_name")
        private String corpName;
        @JsonProperty("corp_name_eng")
        private String corpNameEng;
        @JsonProperty("stock_name")
        private String stockName;
        @JsonProperty("stock_code")
        private String stockCode;
        @JsonProperty("ceo_nm")
        private String ceoNm;
        @JsonProperty("corp_cls")
        private String corpCls;
        @JsonProperty("jurir_no")
        private String jurirNo;
        @JsonProperty("bizr_no")
        private String bizrNo;
        @JsonProperty("adres")
        private String adres;
        @JsonProperty("hm_url")
        private String hmUrl;
        @JsonProperty("ir_url")
        private String irUrl;
        @JsonProperty("phn_no")
        private String phnNo;
        @JsonProperty("fax_no")
        private String faxNo;
        @JsonProperty("induty_code")
        private String indutyCode;
        @JsonProperty("est_dt")
        private String estDt;
        @JsonProperty("acc_mt")
        private String accMt;
}
