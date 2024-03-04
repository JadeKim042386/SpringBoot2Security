package joo.example.springboot2security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INTERNAL_SERVER_ERROR_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    // Auth
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_USER(HttpStatus.NOT_FOUND, "이메일과 일치하는 유저가 존재하지 않습니다."),
    NOT_EQUAL_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임이 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.FORBIDDEN, "가입되지 않은 유저입니다."),
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, "기업을 찾을 수 없습니다."),
    FAILED_REISSUE(HttpStatus.INTERNAL_SERVER_ERROR, "Access Token을 재발행하는데 실패했습니다."),
    EXCEED_REISSUE(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh Token의 잔여 reissue count가 없습니다."),
    NOT_EQUAL_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh Token이 일치하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "Refresh Token이 없습니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "Token이 만료되었습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
