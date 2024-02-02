package com.example.codestates.bgcolor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BgColorResponseDto {
    private Long bgColorId; // Primary Key 필드입니다.
    private String colorName; // 색상의 이름을 저장하는 필드입니다.
    private String hexCode; // 색상의 Hex 코드를 저장하는 필드입니다.

    // 색상 이름과 HEX 코드를 "색상이름: hex코드" 형태의 문자열로 반환하는 메소드
    public String getColorInfo() {
        return colorName + ": " + hexCode;
    }
}
