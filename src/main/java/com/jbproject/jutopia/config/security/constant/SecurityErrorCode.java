package com.jbproject.jutopia.config.security.constant;

import com.jbproject.jutopia.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements SecurityError {

    FORBIDDEN_ERROR_01(403,"Security.403.01", "Forbidden")
    ,FORBIDDEN_ERROR_02(403,"Security.403.02","접근이 허용되지 않은 경로입니다.")

    ,LOGIN_ERROR_01(404,"LOGIN.404.01","아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.")

    ,JWT_AUTH_ERROR_01(500,"JwtAuth.500.01","유효하지 않은 토큰입니다.")
    ,JWT_AUTH_ERROR_02(500,"JwtAuth.500.02","만료된 토큰입니다.")
    ,JWT_AUTH_ERROR_03(500,"JwtAuth.500.03","지원하지 않느 토큰입니다.")
    ,JWT_AUTH_ERROR_04(500,"JwtAuth.500.04","비정상적인 토큰입니다.")
    ,JWT_AUTH_ERROR_05(500,"JwtAuth.500.05","비정상적인 서명의 토큰입니다.")
    ,JWT_AUTH_ERROR_06(500,"JwtAuth.500.06","Jwt Parsing Error")

    ,JWT_AUTH_ERROR_07(500,"JwtAuth.500.07","로그인 만료입니다.")
    ;

    private int statusCode;
    private String errorCode;
    private String errorMsg;
}
