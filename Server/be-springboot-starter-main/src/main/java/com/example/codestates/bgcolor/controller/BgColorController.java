package com.example.codestates.bgcolor.controller;

import com.example.codestates.bgcolor.dto.BgColorResponseDto;
import com.example.codestates.bgcolor.dto.BgColorPostDto;
import com.example.codestates.bgcolor.entity.BgColor;
import com.example.codestates.bgcolor.mapper.BgColorMapper;
import com.example.codestates.bgcolor.service.BgColorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/colors")
public class BgColorController {

    private final BgColorService bgColorService;
    private final BgColorMapper mapper;

    public BgColorController(BgColorService bgColorService, BgColorMapper mapper) {
        this.bgColorService = bgColorService;
        this.mapper = mapper;
    }

    // 전체 배경색을 조회하는 메소드입니다.
    // 이 메소드는 "/bgColors" 경로로 들어오는 GET 요청을 처리합니다.
    @GetMapping
    public ResponseEntity getAllBgColors() {
        // 모든 배경색을 조회하고, 그 결과를 colors에 저장합니다.
        List<BgColor> colors = bgColorService.getAllBgColors();
        // colors의 각 요소를 "색상이름: hex코드" 형태의 문자열로 변환하고, 그 결과를 dtoList에 저장합니다.
        List<String> dtoList = colors.stream()
                .map(color -> {
                    BgColorResponseDto bgColorResponseDto = mapper.bgColorToBgColorResponseDto(color);
                    return bgColorResponseDto.getColorInfo();
                })
                .collect(Collectors.toList());
        // dtoList를 반환합니다.
        return ResponseEntity.ok(dtoList);
    }
}