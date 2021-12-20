package com.snp.takealook.api.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.message.AuthException;

@RestControllerAdvice
public class ControllerAdvice {

    @Value
    public static class ExceptionRestResponse {
        int code;
        String message;
    }

    // 토큰이 만료되었을 경우
    @ExceptionHandler(TokenExpiredException.class)
    public ExceptionRestResponse tokenExpired() {
        return new ExceptionRestResponse(401, "토큰이 만료되었습니다.");
    }

    // 권한이 없을 경우
    @ExceptionHandler(AuthException.class)
    public ExceptionRestResponse unauthorized() {
        return new ExceptionRestResponse(401, "권한이 없는 접근입니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionRestResponse illegalArgument() {
        return new ExceptionRestResponse(400, "존재하지 않는 엔티티입니다.");
    }
}
