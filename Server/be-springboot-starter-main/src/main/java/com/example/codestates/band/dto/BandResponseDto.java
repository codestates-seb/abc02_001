package com.example.codestates.band.dto;


import com.example.codestates.band.entity.Band;
import com.example.codestates.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class BandResponseDto {


    public long bandId;
    private String school;//학교명
    private String schoolCode;//학교코드 1. 초등학교 2.중학교 3.고등학교
    private int grade; //학년 초등학교 선택시 1~6까지만.7이상 불가. 중고등학교 선택시 1~3학년까지만 4이상불가.
    private int banNumber;//반
    private String joinPass; //반 가입 신청시 비밀번호
    private Band.StatusUpdate statusUpdate; // 신청중인 상태를 나타냄



}
