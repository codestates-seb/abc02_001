package com.example.codestates.auth.handler;

import com.example.codestates.response.ErrorResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {//(1)직접 정의하는 Custom AuthenticationFailureHandler는 AuthenticationFailureHandler 인터페이스를 구현
    //(1)
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException{
        log.error("#Authentication failed:{}",exception.getMessage());

        sendErrorResponse(response);
        //(2) 바로 아래있는 sendErrorResponse()메서드를 호출해 출력 스트림에 Error 정보를 담는다
    }
    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        Gson gson = new Gson(); //(2-1) JSON문자열로 변환하는 데 사용되는 Gson 라이브러리 인스턴스 생성
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED);//(2-2)errorResponse 객체 생성, UnAuthorized(401) 상태코드 전달
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);//(2-3)response의 contentType이 "apllication/jaon"이라는 것을 클라이언트에 알려줄 수 있도록MediaType.APPLICATION_JSON_VALUE를 HTTP Header에 추가
        response.setStatus(HttpStatus.UNAUTHORIZED.value());//(2-4)response의 status가 401임을 클라이언트에게 알려줄 수 있도록 httpHeader에 추가
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));//(2-5)ErrorResponse 객체를 Json 포맷 문자열로 변환 후 출렧트림을 생성

    }
}
