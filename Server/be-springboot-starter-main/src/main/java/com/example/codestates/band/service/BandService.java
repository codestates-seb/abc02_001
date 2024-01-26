package com.example.codestates.band.service;


import com.example.codestates.band.entity.Band;
import com.example.codestates.band.repository.BandRepository;
import com.example.codestates.exception.BusinessLogicException;
import com.example.codestates.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BandService {

    private final BandRepository bandRepository;

    public BandService(BandRepository bandRepository){this.bandRepository = bandRepository;}


    //생성 관련
    public Band createBand(Band band) {

        String school = band.getSchool();
        String schoolCode = band.getSchoolCode();
        int grade = band.getGrade();
        int banNumber = band.getBanNumber();


        verifyExistBand(school, schoolCode, grade, banNumber);
        band.setSchool(school);
        band.setGrade(grade);
        band.setBanNumber(banNumber);
        band.setSchoolCode(schoolCode);
        //학교명, 학년, 반이 중복인지 조회하는 코드


        if("초등학교".equals(schoolCode)){
            if (grade > 6) {
                throw new IllegalArgumentException("초등학교의 경우 1학년부터 6학년까지만 선택이 가능합니다.");
            }//if
        } else if ("중학교".equals(schoolCode) || "고등학교".equals(schoolCode)) {
            if (grade>3){
                throw new IllegalArgumentException("중학교 또는 고등학교의 경우 4학년을 초과 입력 할 수 없습니다.");
            }//if
        }else {
            throw  new IllegalArgumentException("유효하지 않은 학교코드명입니다.");
        }
        // 중학교, 고등학교의 경우 4학년 이상 입력할 수 없게 하는 코드.

        return bandRepository.save(band);

   }

    /* 반 가입신청시, 해당학교명, 학년, 반이 이미 등록되어 있는지 확인하는 코드입니다. 학교, 학년, 반이 하나라도
       겹치지 않을 경우 데이터베이스에 정상적으로 등록 될 것이며, 학교, 학년, 반이 모두 중복일 경우 에러코드 409와 함께 Band already exist
       라는 메세지를 송출합니다. */




    //수정관련
    public Band updateBand(Band band){

        Band findBand = findVerifiedBandId(band.getBandId());

        Optional.ofNullable(band.getSchool()).ifPresent(school ->
                findBand.setSchool(school));

        Optional.ofNullable(band.getSchoolCode()).ifPresent(schoolCode ->
                findBand.setSchoolCode(schoolCode));

        Optional.ofNullable(band.getGrade()).ifPresent(grade ->
                findBand.setGrade(grade));

        Optional.ofNullable(band.getBanNumber()).ifPresent(banNumber ->
                findBand.setBanNumber(banNumber));

        Optional.ofNullable(band.getJoinPass()).ifPresent(joinPass ->
                findBand.setJoinPass(joinPass));

        Optional.ofNullable(band.getStatusUpdate()).ifPresent(statusUpdate ->
                findBand.setStatusUpdate(statusUpdate));

        return bandRepository.save(findBand);


    }


//    public Band findBandId(long bandId){
//        return findVerifiedBandByQuery(bandId);
//    }
//
//
//    public Page<Band> findBandIds(int page, int size){
//        return bandRepository.findAll(PageRequest.of(page,size, Sort.by("bandId").descending()));
//        //BandID를 기준으로 내림차순으로 조회함.
//    }

    public Band findBand(long bandId) { return findVerifiedBandByQuery(bandId);}


    public Page<Band> findBands(int page, int size){
        return bandRepository.findAll(PageRequest.of(page,size, Sort.by("bandId").descending()));
        //학교를 기준으로 내림차순으로 조회함.
    }


//    public Band findSchool(String school){
//        return findVerifiedBandByQuery_S(school);
//    }

//    public Page<Band> findSchools(int page, int size){
//        return bandRepository.findAll(PageRequest.of(page,size, Sort.by("school").descending()));
//        //학교를 기준으로 내림차순으로 조회함.
//    }


    //조회관련(학교, 학교코드, 학년, 반을 모두 사용할 경우)


    public Band findBandDetails(String school, String schoolCode, int grade, int banNumber){

        return findVerifiedBandByDetails(school, schoolCode, grade, banNumber);

    }

    private Band findVerifiedBandByDetails(String school, String schoolCode, int grade, int banNumber){

        Optional<Band> optionalBand = bandRepository.findBySchoolAndSchoolCodeAndGradeAndBanNumber(
                school,schoolCode,grade,banNumber);

        Band findBandDetails = optionalBand.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.BAND_NOT_FOUND));


        return  findBandDetails;
    }





    //삭제관련
    public void deleteBands(long bandId){

        Band band = findVerifiedBandId(bandId);
        bandRepository.delete(band);

    }

    public Band findVerifiedBandId(Long bandId){

        Optional<Band> optionalBand = bandRepository.findByBandId(bandId);
        Band findBandId = optionalBand.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.BAND_NOT_FOUND));


        return findBandId;
    } //수정관련 , 삭제관련,


    private void verifyExistBand(String school, String schoolCode,  int grade, int banNumber) {
        Optional<Band> band = bandRepository.findBySchoolAndSchoolCodeAndGradeAndBanNumber(school,schoolCode, grade, banNumber);
        if(band.isPresent())
            throw new BusinessLogicException(ExceptionCode.BAND_ALREADY_EXIST);
    }
    //중복 여부 검증 메서드


    private Band findVerifiedBandByQuery(long bandId) {
        Optional<Band> optionalBand = bandRepository.findByBandId(bandId);
        Band findBand =
                optionalBand.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.BAND_NOT_FOUND));

        return findBand;

    } // 고유ID로 검색.




    public Band saveBand(Band band){

        return bandRepository.save(band);

    }//더미데이터 생성 관련








}
