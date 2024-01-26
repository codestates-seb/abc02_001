package com.example.codestates.member;

import com.example.codestates.band.entity.Band;
import com.example.codestates.band.service.BandService;
import com.example.codestates.member.entity.Member;
import com.example.codestates.member.service.MemberService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;


//밴드 더미데이터 생성 클래스
@Component
public class MemberInittializer implements ApplicationRunner {


    private final MemberService memberService;
    private final BandService bandService;

    public MemberInittializer(MemberService memberService, BandService bandService) {
        this.memberService = memberService;
        this.bandService = bandService;
    }


    @Override
    public void run(ApplicationArguments args) {
        Band band = new Band("고학", "고등학교",2, 4, "test11", Band.StatusUpdate.SignUp);
        Band savedBand = bandService.saveBand(band);

// 멤버 생성
        Member member = new Member();
        member.setNickname("kevin");
        member.setPassword("{bcrypt}$2a$10$p0VxN9dfkvwzQWDLPlotc.xs3PL8wkPuqI3SLnTR7LV6eBc/ngnBW");
        member.setEmail("user@example.com");
        member.setRoles(Collections.singletonList("ROLE_USER"));

// 멤버를 밴드에 연결
        member.setBand(savedBand);  // bandId를 직접 설정하지 않고 저장된 밴드 객체를 연결

// 멤버 저장
        memberService.saveMember(member);

    }
}
