package com.example.codestates.member.dto;

import com.example.codestates.bgcolor.entity.BgColor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPatchDto {

    private Long memberId;

    private String nickName;

    private BgColor bgColor;
}
