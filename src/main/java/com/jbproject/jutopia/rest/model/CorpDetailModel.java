package com.jbproject.jutopia.rest.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class CorpDetailModel {
    /*
     * corp_code 기업코드
     * corp_name 정식회사명칭
     * corp_name_eng 영문명칭 영문정식회사명칭
     * stock_name 종목명(상장사) 또는 약식명칭(기타법인)
     * stock_code 상장회사인 경우 주식의 종목코드 상장회사의 종목코드(6자리)
     * ceo_nm 대표자명 대표자명
     * corp_cls 법인구분 법인구분 : Y(유가), K(코스닥), N(코넥스), E(기타)
     * jurir_no 법인등록번호
     * bizr_no 사업자등록번호
     * adres 주소
     * hm_url 홈페이지
     * ir_url IR홈페이지
     * phn_no 전화번호
     * fax_no 팩스번호
     * induty_code 업종코드
     * est_dt 설립일(YYYYMMDD)
     * acc_mt 결산월(MM)
     */

        private String corp_code;
        private String corp_name;
        private String corp_name_eng;
        private String stock_name;
        private String stock_code;
        private String ceo_nm;
        private String corp_cls;
        private String jurir_no;
        private String bizr_no;
        private String adres;
        private String hm_url;
        private String ir_url;
        private String phn_no;
        private String fax_no;
        private String induty_code;
        private String est_dt;
        private String acc_mt;
}
