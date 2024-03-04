package joo.example.springboot2security.service;

import joo.example.springboot2security.exception.AuthException;
import joo.example.springboot2security.exception.ErrorCode;
import joo.example.springboot2security.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final TokenProvider tokenProvider;

    public String signIn(String email, String password) {
        //비밀번호 일치 확인
        if (!password.equals("admin")) {
            throw new AuthException(ErrorCode.INVALID_PASSWORD);
        }
        //AccessToken 발급
        return tokenProvider.generateAccessToken(
                email, "admin", String.format("%s:%s", 1, "ADMIN"));
    }
}
