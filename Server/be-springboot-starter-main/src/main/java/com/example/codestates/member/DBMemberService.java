//package com.example.codestates.member;
//
//import com.example.codestates.member.entity.Member;
//import com.example.codestates.member.repository.MemberRepository;
//import com.example.codestates.member.service.MemberService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//@Transactional
//public class DBMemberService implements MemberService {
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public DBMemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//    }//생성자를 통해 MemberRepository PasswordEncoder Bean 객체를 DI
//
//@Override
//    public Member createMember(Member member){
//        verifyExistEmail(member.getEmail());
//        String encryptedPassword = passwordEncoder.encode(member.getPassword());  //PasswordEncoder를 이용해 패스워드를 암호화
//        member.setPassword(encryptedPassword);    // 암호화된 패스워드를 다시 passoword 필드에 할당
//
//        Member savedMember = memberRepository.save(member);
//
//        System.out.println("# Create Member in DB");
//        return savedMember;
//  }
//}
