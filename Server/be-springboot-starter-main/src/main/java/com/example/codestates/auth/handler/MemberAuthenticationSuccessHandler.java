package com.example.codestates.auth.handler;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.example.codestates.auth.userdetails.MemberDetailsService;
import com.example.codestates.member.dto.MemberResponseDto;
import com.example.codestates.member.entity.Member;
import com.example.codestates.member.repository.MemberRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
우리가 직접 정의하는 Custom AuthenticationSuccessHandler는 AuthenticationSuccessHandler 인터페이스를 구현해야 합니다.
AuthenticationSuccessHandler 인터페이스에는 onAuthenticationSuccess() 추상 메서드가 정의되어 있으며
 onAuthenticationSuccess() 메서드를 구현해서 추가 처리를 하면 됩니다.
 */
@Slf4j
//@RequiredArgsConstructor
@Component
@Transactional
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    //private final MemberRepository memberRepository;

    //Authentication 객체에 사용자 정보를 얻은 후 ,HttpServletResponse로 출력 스트림을 생헝하여 response를 전송할 수 있다
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("# Authenticated successfully");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("email", authentication.getName());
        responseData.put("authorities", authentication.getAuthorities());
        //여기에 username(email)을 가지고 와서 사용자 정보들을 반환하는 코드 작성해야함
        responseData.put("nickname", ((MemberDetailsService.MemberDetails) userDetails).getNickname());
        responseData.put("memberId",((MemberDetailsService.MemberDetails) userDetails).getMemberId());

        responseData.put("mbti",((MemberDetailsService.MemberDetails) userDetails).getMbti());


        //아니면 responsdto로 작성해서 확인해야함


        // Gson 객체 생성
        Gson gson = new Gson();

        // Content Type을 JSON으로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 데이터를 JSON 형식으로 변환하고 Response Body에 쓰기
        response.getWriter().write(gson.toJson(responseData));

        // 이외에 필요한 HTTP 상태 코드 등을 설정할 수 있습니다.

    }
}

