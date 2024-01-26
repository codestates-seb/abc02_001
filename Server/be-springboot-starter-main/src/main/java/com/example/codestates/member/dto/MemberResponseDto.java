package com.example.codestates.member.dto;

import com.example.codestates.bgcolor.entity.BgColor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor

public class MemberResponseDto {
    private Long memberId;
    private String nickname;
    private String email;
    private String bgColorInfo;// 색상,색상값을을 "색상:색상값" 형태로 바꿔 저장하는 필드

    // BgColor 객체를 "색상:색상값" 형태의 문자열로 변환하는 메소드
    public void setBgColor(BgColor bgColor) {
        this.bgColorInfo = bgColor.getColorName() + ": " + bgColor.getHexCode();
    }
}
