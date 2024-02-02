package com.example.codestates.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class CustomAuthorityUtils {


    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

    private final List<String> USER_ROLES_STRING = List.of("USER");

    public List<GrantedAuthority> createAuthorities(String email){

        return USER_ROLES;
    }


    //DB에 저장된 role을 기반으로 권한 정보 생성 yml파일 필요없음
    public List<GrantedAuthority> createAuthorities(List<String> roles){
        List<GrantedAuthority> authorities =roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return authorities;
    }
    //DB에 roles를 저장하는 코드
    public List<String> createRoles(String email){
        return USER_ROLES_STRING;
    }
}
