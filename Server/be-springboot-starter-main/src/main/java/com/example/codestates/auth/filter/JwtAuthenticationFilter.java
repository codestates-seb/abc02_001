package com.example.codestates.auth.filter;

import com.example.codestates.member.entity.Member;
import com.example.codestates.auth.dto.LoginDto;
import com.example.codestates.auth.jwt.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {//(1)UsernamePasswordAuthenticationFilter는 폼 로그인 방식에서 사용하는 디폴트 Security Filter,폼 로그인이 아니더라도 Username/Password 기반의 인증을 처리하기 위해 UsernamePasswordAuthenticationFilter를 확장해서 구현
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    //(2) DI 받은 AuthenticationManager는 로그인 인증 정보를 전달 받아 UserDetailsService와 인터랙션 한 뒤 인증 여부를 판단
    //JwtTokenizer는 클라이언트가 인증에 성공할 경우, JWT를 생성 및 발급하는 역할을 합니다.
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }

    //(3) 메서드 내부에서 인증을 시도하는 로직을 구현하면 됨
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        ObjectMapper objectMapper = new ObjectMapper(); //(3-1) DTO클래스로 역직렬화 하기위해 ObjectMApper를 인스턴스 생성한다.
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class); //(3-2)ServeltInputStream을 LoginDto클래스의 객체로 역질렬화한다.
        //(3-3)username과 password 정보를 포함한 UsernamePasswordAuthenticationToken을 생성한다.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);//(3-4)AuthenticationManager에게 전달하여 인증 처리를 위임한다.
    }

    //(4)인증에 성공할 경우 호출
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException{
        Member member = (Member) authResult.getPrincipal(); //(4-1) member엔티티 클래스의 객체를 얻는다 인증이 성공하면 인증된 authentication객체가 생성되면서 principal필드에 member객체 할당

        String accessToken = delegateAccessToken(member); //(4-2) Access Token 생성
        String refreshToken = delegateRefreshToken(member);//(4-3)Refresh Token 생성

        response.setHeader("Authorization","Bearer "+accessToken); //(4-4) response header에 Access Token을 추가
        //클라이언트 측에서 벡엔드 애플리케이션 측에 요청을 보낼 때 마다 request header에 추가해서 클라이언트 측의 자격을 증명하는데 사용
        response.setHeader("Refresh",refreshToken); //(4-5) response header에 Refresh Token을 추가
        //Access Token이 만료될 경우, 클라이언트 측이 Access Token을 새로 발급받기 위해 클라이언트에게 추가적으로 제공 Refresh Token을 Access Token과 함께 클라이언트에게 제공할지 여부는 애플리케이션의 요구 사항에 따라 달라짐
        this.getSuccessHandler().onAuthenticationSuccess(request,response,authResult);
        //로그인 인증에 성공하고 JWT를 생성해서 response header에 추가한뒤,AuthenticationSuccessHandler의 onAuthenticationSuccess() 메서드를 호출
        //failureHandler는 별도 코드를 추가하지 않아도 로그인 인증 실패시 구현,
    }


    //여기에 토큰에 넣을 정보를 작성 하는 것 인가?
    //(5) AccessToken을 생성하는 구체적인 로직
    private String delegateAccessToken(Member member){
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId",member.getMemberId());
        claims.put("username",member.getEmail());
        claims.put("role",member.getRoles());
        claims.put("nickname", member.getNickname());

        String subject = member.getEmail();

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    //(6) Refresh Token을 생성하는 구체적인 로직
    private String delegateRefreshToken(Member member){
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
