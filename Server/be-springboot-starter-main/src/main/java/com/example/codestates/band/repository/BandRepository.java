package com.example.codestates.band.repository;


import com.example.codestates.band.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//데이터베이스 생성을 위한 엔티티 작업(CREATE, READ, UPDATE, DELETE)을 수행 하기 위한 인터페이스임
public interface BandRepository extends JpaRepository<Band, Long> {

    //JpaRepository가 CREATE, READ, UPDADTE, DELETE작업을 제공함.
    //직업 SQL 파일을 삽입하여 사용할 수도 있음.
    //그러나 현 프로젝트는 배운것을 최대한 복습하는 것이므로, 직접 SQL입력은 지양하며, 최대한 spring DATA SPA를 사용하도록함.
    //특별한 경우에만 직접 SQL을 사용해야 한다면 해당 내용을 주석으로 추가.

    Optional<Band> findByStatusUpdate(String status); //status값을 기준으로 조회
    Optional<Band> findByBandId(long bandId); //밴드Id를 기준으로 조회

    Optional<Band> findBySchoolAndSchoolCodeAndGradeAndBanNumber(String school, String schoolCode, int grade, int banNumber);
    //쿼리문 없어도, school, grade, bannum dmf 기반으로한 쿼리를 자동생성함(Spring Data JPA)


    //@Query(value = "SELECT c FROM Band c WHERE c.school = :school")
    Optional<Band> findBySchool(String school); //학교이름을 기준으로 조회
    //List<Band> findBySchool(@Param("school")String school);

    //List<Band> findBySchool(String school);

}
