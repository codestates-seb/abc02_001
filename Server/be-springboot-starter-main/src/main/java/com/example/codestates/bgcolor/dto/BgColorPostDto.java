package com.example.codestates.bgcolor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BgColorPostDto { //BgColorInitializer에서 색상 저장을 하고 있기에 저장(생성)용 dto 필요없는듯함(파일타입을 바꿔둿음)
    private String colorName; // 색상의 이름을 저장하는 필드입니다.
    private String hexCode; // 색상의 Hex 코드를 저장하는 필드입니다.
}
