package com.example.codestates.band;

import com.example.codestates.band.entity.Band;
import com.example.codestates.band.service.BandService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


//밴드 더미데이터 생성 클래스
@Component
public class BandInitializer implements ApplicationRunner {


    private final BandService bandService;

    public BandInitializer(BandService bandService) {
        this.bandService = bandService;
    }

    @Override
    public void run(ApplicationArguments args) {


        bandService.saveBand(new Band("김해", "초등학교",5, 8, "test11",
                Band.StatusUpdate.SignUp));
        bandService.saveBand(new Band("울산", "초등학교",6, 1,"test11",
                Band.StatusUpdate.SignUp));
        bandService.saveBand(new Band("창원", "중학교",1, 1, "test11",
                Band.StatusUpdate.SignUp));
        bandService.saveBand(new Band("마산", "고등학교",1, 3,"test11",
                Band.StatusUpdate.SignUp));
        bandService.saveBand(new Band("창녕", "고등학교",2, 4, "test11",
                Band.StatusUpdate.SignUp));


    }
}
