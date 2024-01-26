package com.example.codestates.auth.userdetails;



import com.example.codestates.bgcolor.entity.BgColor;
import com.example.codestates.mbti.entity.Mbti;
import com.example.codestates.member.entity.Member;
import com.example.codestates.member.repository.MemberRepository;
import com.example.codestates.auth.utils.CustomAuthorityUtils;
import com.example.codestates.exception.BusinessLogicException;
import com.example.codestates.exception.ExceptionCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
        import java.util.Optional;
//user의 인증 정보를 테이블에 저장하고, 테이블에 저장된 인증 정보를 이용해 인증 프로세스를 진행

//데이터베이스의 인증 정보로 인증을 처리하는 custom UserDetailsService
@Component
@Transactional
public class MemberDetailsService implements UserDetailsService { //(1)UserDetailsService 인터페이스 구현
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;

//(2)데이터베이스에서 User를 조회하고 조회한 User의 권한 정보를 생성하기위해 DI를 받음
    public MemberDetailsService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }
    //(3)UserDetailsService 인터페이스를 implements하는 구현 클래스는 loadUserBuUsername(String username)이라는 추상 메서드를 구현해야함
    //UserDetails는 UserDetailsService에 의해 load 되어 인증을 위해 사용되는 핵심 User 정보를 표현하는 인터페이스
    //UserDetails 인터페이스 구현체는 스프링 시큐리티에서 보안 정보 제공을 목적으로 직접 사용 안됨, authentication 객체로 캡슐화되어 제공
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Member> optionalMember = memberRepository.findByEmail(username);
        Member findMember = optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //(4) 데이터베이스에서 조회한 회원의 이메일 정보를 이용해 Role 기반의 권한 정보(GrantedAuthority)컬랙션을 생성

        //(5) 데이터베이스에서 조회한 인증정보와 4에서 생성한 권한 정보를 스프링 시큐리티에선 아직 알지 못하기 때문에 스프링 시큐리티에 정보들을 제공해야 하며,
        // UserDetails 인터페이스의 구현체인 User클래스의 객체를 통해 제공
        //데이터베이스에서 조회한 User클래스의 객체를 리턴하면 스프링 시큐리티가 이 정보를 이용해 인증 절차를 수행
        //데이터베이스에서 User의 인증 정보만 스프링 시큐리티에 넘겨주고 인증 처리는 스프링 시큐리티가 대신해줌
        //>>>
        //>>>
        //>>>
        return new MemberDetails(findMember);
        //>>> Custom MemberDetails 클래스의 생성자로 findMember를 전달하면서 코드가 깔끔해짐
    }


   //(2) Details 클래스 추가 Collection<?~ 가 클래스 내부로 포함되었음


    public final class MemberDetails extends Member implements UserDetails { //(2-1)    //UserDetails 인터페이스를 구현, Member 엔티티 클래스 상속
        //여기도 추가 멤버 정보 넣어야하나?
        //>데이터베이스에서 조회한 회원 정보를 스프링 시큐리티의 User정보로 변환하는 과정과 User의 권한 정보를 생서하는 과정을 캡슐화
        //member엔티티 클래스를 상속하고 있으므로 memberDetails를 리턴 받아 사용하는 측에서는 두 개 클래스의 객체를 모두다 손쉽게 캐스팅해서 사용 가능
       //(2-2)
        public MemberDetails(Member member) {
            setMemberId(member.getMemberId());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());//member에 데이트 베이스에서 조회한 role을 전달
            setMbti(member.getMbti());
            setNickname(member.getNickname());
            setMbti(member.getMbti());
//          //bandID들고와서 설정하기
//          // 관심사 설정하기



        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return authorityUtils.createAuthorities(this.getRoles()); //(2-3) 리팩토링 포인트
            // member에 전달한 role 정보를 메서드의 파리미터로 전달하여 권한 목록을 생성하고있다
        }


        //갑자기 이게 안넣어지면 오류가 뜬다 왜?-> 명칭이 달라서 그런거

        //(2-4)스프링 시큐리티에서 인식 할 수있는 username을 member클래스의 email 주소로 채우고 있다. 리턴값은 null일 수 없다.
        @Override
        public String getUsername() {
            return getEmail();
        }
        //respons에 들어가야할 정보들에 대한 메서드들을 정의
        public Long getMemberId(){return super.getMemberId();}
        public Mbti getMbti(){return super.getMbti();}
        public String getNickname(){return super.getNickname();}
        public BgColor getBgColor(){return super.getBgColor();}//오버스택 조심하기


        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
