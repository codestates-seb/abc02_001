package com.example.codestates.bgcolor;

import com.example.codestates.bgcolor.entity.BgColor;
import com.example.codestates.bgcolor.service.BgColorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BgColorInitializer implements ApplicationRunner {
    private final BgColorService bgColorService;

    public BgColorInitializer(BgColorService bgColorService) {
        this.bgColorService = bgColorService;
    }

    @Override
    public void run(ApplicationArguments args) {
        // 배경색을 저장합니다
        BgColor defaultColor = new BgColor("BROWN", "#f4eeee");
        bgColorService.saveBgColor(defaultColor);

        bgColorService.saveBgColor(new BgColor("ORANGE", "#faebdd"));
        bgColorService.saveBgColor(new BgColor("YELLOW", "#fbf3db"));
        bgColorService.saveBgColor(new BgColor("GREEN", "#edf3ec"));
        bgColorService.saveBgColor(new BgColor("BLUE", "#e7f3f8"));
        bgColorService.saveBgColor(new BgColor("PURPLE", "#f6f3f9"));
        bgColorService.saveBgColor(new BgColor("PINK", "#faf1f5"));
        bgColorService.saveBgColor(new BgColor("RED", "#fdebec"));

        // 기본 배경색을 설정합니다.
        bgColorService.setDefaultBgColor(defaultColor);
    }
}