package com.example.codestates.config;


import com.example.codestates.auth.filter.JwtAuthenticationFilter;
import com.example.codestates.auth.filter.JwtVerificationFilter;
import com.example.codestates.auth.handler.MemberAccessDeniedHandler;
import com.example.codestates.auth.handler.MemberAuthenticationEntryPoint;
import com.example.codestates.auth.handler.MemberAuthenticationFailureHandler;
import com.example.codestates.auth.handler.MemberAuthenticationSuccessHandler;
import com.example.codestates.auth.jwt.JwtTokenizer;
import com.example.codestates.auth.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
/*Spring Security Configuration을 적용하면 우리가 원하는 인증 방식과 웹 페이지에 대한 접근 권한을 설정
* 기본 구조는 심플하다 @configuration만 추가해 주는 것이 전부*/
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils; //추가


    public SecurityConfig(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //http security를 통해 HTTP 요청에 대한 보안 설정을 구성하는 핵심 클래스. Bean으로 등록해서 보안설정 구성하는 방식 권장
        http
                .headers().frameOptions().sameOrigin()//frameOptions()>>html 태그중 frame이나 iframe, object 태그에서 페이지를 렌더링 할지의 여부를 결정 기본적으로 deny
                //.frameOptions().sameOrigin() 동일 추처로부터 들어오는 request만 페이지 렌더링을 허용
                .and()
                .csrf().disable()//CSRF 공격에 대한 스프링 시큐리티 설정을 비활성화 CSRF token 검증 x
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//(1) 세션을 생성하지 않도록 설정 "Always"는 항상 세션 생성, NEVER 생성X 있다면 사용, IF Required 필요한 경우 생성
                .and()
                .formLogin().disable()//기본적인 인증 방법을 폼 로그인 방식으로 지정> CSR은 지정 안함
                .httpBasic().disable() //request를 전송할 때마다 username/password 정보를 http header에 실어 인증을 하는 방식 >지금은 사용안함 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())// custom Configurer를 추가해 커스터마이징된 configuration을 추가할 수 있다.
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST,"/*/users").permitAll()
                        .antMatchers(HttpMethod.PATCH,"/*/users/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/*/users/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/*/users/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/*/bands").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/*/bands/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/*/bands/**").hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/*/comments").hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/*/comments/**").hasRole("USER")
                        .antMatchers(HttpMethod.PATCH,"/*/comments/**").hasRole("USER")
                        .antMatchers(HttpMethod.DELETE,"/*/comments/**").hasRole("USER")
                        .anyRequest().permitAll())
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                .logoutSuccessUrl("/")
                .and();

        return http.build();
    }
    //(10)스프링 시큐리티에서 제공하는 패스워드 암호화 기능을 제공 컴포넌트 디폴트는 bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    //구체적인 CORS 정책을 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"
                ,"http://localhost:3000"
        ,"http://ec2-52-78-187-199.ap-northeast-2.compute.amazonaws.com:8080")); //모든 출저에 대해 스크립트 기반의 HTTP 통신을 허용하도록 설정, 운영 서버 환경 요구사항에 맞게 변경 가능
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE")); //파라미터로 지정한 HTTP Method에 대한 HTTP 통신 허용
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh", "Location"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // CorsConfigurationSource 인터페이스의 구현 클래스인 UrlBasedCorsConfigurationSource 클래스의 객체를 생성
        source.registerCorsConfiguration("/**",configuration); //모든 url에 앞에서 구성한 CORS 정책을 적용
        return source;
    }


    //(2) JwtAuthenticationFilter를 등록하는 역할
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer,HttpSecurity>{ //2-1 AbstractHttpConfigurer Custom configurer 구현할 수있다
        //AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity>와 같이 AbstractHttpConfigurer를 상속하는 타입과 HttpSecurityBuilder를 상속하는 타입을 제너릭 타입으로 지정할 수 있다

        @Override
        public void configure(HttpSecurity builder) throws Exception { //2-2 Configuration을 커스터마이징 할 수 있다
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); //2-3 AuthenticationManager의 객체를 얻을 수 있다.
            //getSharedObject를 통해 스프링 시큐리티의 설정을 구성하는 SecurityConfigurer간에 공유되는 객체를 얻을 수 있다
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer); //2-4 JwtAuthenticationFilter를 생성하면서 JwtAuthenticationFilter에서 사용되는 AuthenticationManager와 JwtTokenizer를 DI
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login"); //2-5 디폴트 request URL인 /login을 /users/login으로 변경
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());//successHandler 추가
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());//FailureHandler 추가
            /*
            AuthenticationSuccessHandler와 AuthenticationFailureHandler 인터페이스의 구현 클래스가 다른 Security Filter에서 사용이 된다면 ApplicationContext에 Bean으로 등록해서 DI 받는 게 맞습니다.
            하지만 일반적으로 인증을 위한 Security Filter마다 AuthenticationSuccessHandler와 AuthenticationFailureHandler의 구현 클래스를 각각 생성할 것이므로 new 키워드를 사용해서 객체를 생성해도 무방합니다.
             */

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer,authorityUtils);//(2)추가JwtVerificationFilter의 인스턴스를 생성하면서 JwtVerificationFilter에서 사용되는 객체들을 생성자로 DI

            builder
                    .addFilter(jwtAuthenticationFilter) //2-6 addFilter를 통해 JwtAuthenticationFilter를 Spring Security Filter Chain에 추가합니다.
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
            //(3)추가 JwtVerificationFilter를 JwtAuthenticationFilter 뒤에 추가합니다.
            //JwtVerificationFilter는 JwtAuthenticationFilter에서 로그인 인증에 성공한 후
            // 발급받은 JWT가 클라이언트의 request header(Authorization 헤더)에 포함되어 있을 경우에만 동작합니다.

        }
    }
}
