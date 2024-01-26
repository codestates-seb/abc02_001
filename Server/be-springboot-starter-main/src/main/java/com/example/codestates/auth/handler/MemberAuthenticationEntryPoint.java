package com.example.codestates.auth.handler;

import com.example.codestates.auth.utils.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //AuthenticationEntryPoint를 구현한 MemberAuthenticationEntryPoint
    //MemberAuthenticationEntryPoint 클래스는 인증 과정에서 AuthenticationException 이 발생할 경우 호출
    //처리하고자 하는 로직을 commence() 메서드에 구현하면 됩니다.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception exception =(Exception) request.getAttribute("exception");
        ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);

        logExceptionMessage(authException,exception);
    }
    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        // 주로 발생하는 하위 Exception: SignatureException, ExpiredJwtException, MalformedJwtException
        String message = exception != null ? exception.getMessage() : authException.getMessage();
        log.warn("Unauthorized error happened: {}", message);

        if (exception != null) {
            log.warn("Exception type: {}", exception.getClass());
        }
    }
}
