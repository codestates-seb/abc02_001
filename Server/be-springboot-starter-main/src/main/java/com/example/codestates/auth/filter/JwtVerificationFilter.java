package com.example.codestates.auth.filter;

import com.example.codestates.auth.jwt.JwtTokenizer;
import com.example.codestates.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.security.SignatureException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter { //(1) oncePerRequestFilter를 확장해서 request 당 한 번만 실행되는 seurity filter를 구현
    //JWT는 request당 단 한번만 수행하면 되기 때문에 JWT 전용 filter로 만들기에는  위가 적절
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    //(2) JwtTokenizer는 JWT를 검증하고 Claims를 얻는데 사용
    //CustomAuthorityUtils는 JWT 검증에 성공하면 Authentication 객체에 채울 사용자의 권한을 생성하는 데 사용됩
    public JwtVerificationFilter(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils) {
        this.jwtTokenizer =jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }


    //무슨기능인지 다시보기 이해못함
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //(1)
        try {
            Map<String, Object> claims = verifyJws(request); //(3)JWT를 검증하는 데 사용하는 private
            setAuthenticationToContext(claims); //(4)Authentication 객체를 SecurityContext에 저장하기 위한 private
        }
        catch (SignatureException se){
            request.setAttribute("exception",se);
        }
        catch (ExpiredJwtException ee){
            request.setAttribute("exception",ee);
        }
        catch (Exception e) {
            request.setAttribute("exception", e);
        }
        /*
        JwtVerificationFilter 예외 처리의 키포인트는 우리가 일반적으로 알고 있는 예외 처리 방식과는 다르게 Exception을 catch한 후에 Exception을 다시 throw 한다든지 하는 처리를 하지 않고
         단순히 request.setAttribute()를 설정하는 일밖에 하지 않는다는 것입니다.
         예외가 발생하게 되면 SecurityContext에 클라이언트의 인증 정보(Authentication 객체)가 저장되지 않습니다.
         이 상태로 다음(next) Security Filter 로직을 수행하다 보면 결국에는 Filter 내부에서 AuthenticationException이 발생하게 되고,
         이 AuthenticationException은 AuthenticationEntryPoint가 처리하게 됩니다.
         */

        filterChain.doFilter(request, response); //(5) next Security filter 호출
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); //(3-1) request의 header에서 JWT얻음 "Bearer"부분 제거 , jws로 지정한 이유는 서명된 JWT라서
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());//(3-2)JWT 서명을 검증하기 위한 Sercret Key
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();//(3-3) JWT에서 Claims를 파싱 가능>내부적으로 서명 검증에 성공했다는 의미

        return claims;
    }

    //(6) 특정 조건에 부합하면 해당 filter의 동작을 수행하지 않고 다음 filter로 건너뛰도록 해준다
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization"); //(6-1) authorization header의 값을 얻은 후에

        return authorization == null || !authorization.startsWith("Bearer");
        //(6-2)Authorization header의 값이 null이거나 Authorization header의 값이 “Bearer”로 시작하지 않는다면
        // 해당 Filter의 동작을 수행하지 않도록 정의합니다.
        /*
        JWT가 Authorization header에 포함되지 않았다면 JWT 자격증명이 필요하지 않은 리소스에 대한 요청이라고 판단하고 다음(Next) Filter로 처리를 넘기는 것입니다.
        JWT 자격 증명이 필요한 리소스 요청인데 실수로 JWT를 포함하지 않았다 하더라도 이 경우에는 Authentication이 정상적으로 SecurityContext에 저장되지 않은 상태이기 때문에 다른 Security Filter를 거쳐 결국 Exception을 던지게 될 것
         */
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username"); //(4-1)JWT에서 파싱한 Claims에서 username을 덛는다
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles")); //(4-2) List<GrantedAuthority를 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities); //(4-3)username과 List<GrantedAuthority를 포함한 Authentication 객체를 생성
        SecurityContextHolder.getContext().setAuthentication(authentication);//(4-4) SecurityContext에 Authentication 객체 저장
        /*
        JWT는 클라이언트 정보 등의 상태를 저장하지 않는 Stateless한 방식인데 SecurityContext에 Authentication을 저장하게 되면 세션의 상태는 어떻게 되는지 궁금해할 수도 있습니다.
        SecurityContext에 Authentication을 저장하게 되면 Spring Security의 세션 정책(Session Policy)에 따라서 세션을 생성할 수도 있고, 그렇지 않을 수도 있습니다.
        JWT 환경에서는 세션 정책(Session Policy) 설정을 통해 세션 자체를 생성하지 않도록 설정합니다.
         */
    }
}
