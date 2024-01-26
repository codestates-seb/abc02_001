package com.example.codestates.auth.handler;

import com.example.codestates.auth.utils.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponder.sendErrorResponse(response, HttpStatus.FORBIDDEN);
        log.warn("Forbidden error happened:{}",accessDeniedException.getMessage());

        //리프레쉬 토큰 검증하고, access 만료 리프레쉬 토큰 생존 시 , access 토큰과 리프레쉬 토큰 재 발생 로직을 작성해야함 (인증 처리 로직 까지 포함)

    }
}
