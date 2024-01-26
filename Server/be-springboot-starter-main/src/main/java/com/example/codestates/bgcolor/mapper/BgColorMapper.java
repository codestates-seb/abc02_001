package com.example.codestates.bgcolor.mapper;

import com.example.codestates.bgcolor.dto.BgColorResponseDto;
import com.example.codestates.bgcolor.dto.BgColorPostDto;
import com.example.codestates.bgcolor.entity.BgColor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")  // MapStruct의 Mapper임을 나타냅니다. 이 Mapper는 Spring Bean으로 등록
public interface BgColorMapper {

    //BgColorPostDto 기재내용 참고
//    BgColor bgColorPostDtoToBgColor(BgColorPostDto bgColorPostDto); // BgColorPostDto -> BgColor 변환

    BgColorResponseDto bgColorToBgColorResponseDto(BgColor bgColor); // BgColor -> BgColorResponseDto 변환
}
