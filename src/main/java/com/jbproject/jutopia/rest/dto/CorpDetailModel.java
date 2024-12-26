package com.jbproject.jutopia.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.jbproject.jutopia.rest.entity.CorpDetailEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@JsonRootName("result")
@JsonIgnoreProperties({"status","message"})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
@Schema(description = "기업 상세 모델")
public class CorpDetailModel {

        @JsonProperty("corp_code")
        @Schema(title = "corp_code", description = "기업코드")
        private String corpCode;
        @JsonProperty("corp_name")
        @Schema(title = "orp_name", description = "정식명칭 정식회사명칭")
        private String corpName;
        @JsonProperty("corp_name_eng")
        @Schema(title = "corp_name_eng", description = "영문명칭 영문정식회사명칭")
        private String corpNameEng;
        @JsonProperty("stock_name")
        @Schema(title = "stock_name", description = "종목명(상장사) 또는 약식명칭(기타법인) 종목명(상장사) 또는 약식명칭(기타법인)")
        private String stockName;
        @JsonProperty("stock_code")
        @Schema(title = "stock_code", description = "상장회사인 경우 주식의 종목코드 상장회사의 종목코드(6자리)")
        private String stockCode;
        @JsonProperty("ceo_nm")
        @Schema(title = "ceo_nm", description = "대표자명")
        private String ceoNm;
        @JsonProperty("corp_cls")
        @Schema(title = "corp_cls", description = "법인구분 법인구분 : Y(유가), K(코스닥), N(코넥스), E(기타)")
        private String corpCls;
        @JsonProperty("jurir_no")
        @Schema(title = "jurir_no", description = "법인등록번호")
        private String jurirNo;
        @JsonProperty("bizr_no")
        @Schema(title = "bizr_no", description = "사업자등록번호")
        private String bizrNo;
        @JsonProperty("adres")
        @Schema(title = "adres", description = "주소")
        private String adres;
        @JsonProperty("hm_url")
        @Schema(title = "hm_url", description = "홈페이지")
        private String hmUrl;
        @JsonProperty("ir_url")
        @Schema(title = "ir_url", description = "IR홈페이지")
        private String irUrl;
        @JsonProperty("phn_no")
        @Schema(title = "phn_no", description = "전화번호")
        private String phnNo;
        @JsonProperty("fax_no")
        @Schema(title = "fax_no", description = "팩스번호")
        private String faxNo;
        @JsonProperty("induty_code")
        @Schema(title = "induty_code", description = "업종코드")
        private String indutyCode;
        @JsonProperty("est_dt")
        @Schema(title = "est_dt", description = "설립일(YYYYMMDD)")
        private String estDt;
        @JsonProperty("acc_mt")
        @Schema(title = "acc_mt", description = "결산월(MM)")
        private String accMt;

        public static CorpDetailModel create(CorpDetailEntity entity) {
                CorpDetailModel model = new CorpDetailModel();

                model.corpCode = entity.getCorpCode();
                model.corpName = entity.getCorpName();
                model.corpNameEng = entity.getCorpName();
                model.stockName = entity.getStockName();
                model.stockCode = entity.getStockCode();
                model.ceoNm = entity.getCeoName();
                model.corpCls = entity.getCorpCls();
                model.jurirNo = entity.getJurirNo();
                model.bizrNo = entity.getBizrNo();
                model.adres = entity.getAddress();
                model.hmUrl = entity.getHmUrl();
                model.irUrl = entity.getIrUrl();
                model.phnNo = entity.getPhnNo();
                model.faxNo = entity.getFaxNo();
                model.indutyCode = entity.getIndutyCode();
                model.estDt = entity.getEstDate();
                model.accMt = entity.getAccMt();
                return model;
        }
}
