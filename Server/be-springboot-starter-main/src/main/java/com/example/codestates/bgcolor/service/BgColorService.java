package com.example.codestates.bgcolor.service;

import com.example.codestates.bgcolor.entity.BgColor;
import com.example.codestates.bgcolor.repository.BgColorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BgColorService {
    private final BgColorRepository bgColorRepository;
    private BgColor defaultBgColor; // 기본 배경색을 저장하는 필드를 추가합니다.

    public BgColorService(BgColorRepository bgColorRepository) {
        this.bgColorRepository = bgColorRepository;
    }

    // 배경색 데이터를 저장하는 메소드
    public BgColor saveBgColor(BgColor bgColor) {
        return bgColorRepository.save(bgColor); // 배경색 데이터를 데이터베이스에 저장
    }

    // 기본 배경색을 설정하는 메소드를 추가합니다.
    public void setDefaultBgColor(BgColor defaultBgColor) {
        this.defaultBgColor = defaultBgColor;
    }

    // 기본 배경색을 조회하는 메소드를 추가합니다.
    public BgColor getDefaultBgColor() {
        if (defaultBgColor == null) {
            throw new IllegalStateException("Default color is not set");
        }
        return defaultBgColor;
    }

    // 모든 배경색 데이터를 불러오는 메소드
    public List<BgColor> getAllBgColors() {
        return bgColorRepository.findAll(); // 데이터베이스에 저장된 모든 배경색 데이터를 불러옴
    }
}
