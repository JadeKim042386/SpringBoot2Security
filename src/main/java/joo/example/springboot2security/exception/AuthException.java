package joo.example.springboot2security.exception;

import lombok.Getter;

@Getter
public class AuthException extends CustomException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(ErrorCode errorCode, Exception causeException) {
        super(errorCode, causeException);
    }
}
