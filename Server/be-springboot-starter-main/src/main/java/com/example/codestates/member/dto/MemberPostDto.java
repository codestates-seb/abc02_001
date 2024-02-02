package com.example.codestates.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPostDto {
    private String nickName;
    private String bgColorName;  // 사용자가 선택한 배경색의 이름
    @NotBlank
    private String password;

}
