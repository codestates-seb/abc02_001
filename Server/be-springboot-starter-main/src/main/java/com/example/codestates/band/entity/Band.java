package com.example.codestates.band.entity;

import com.example.codestates.audit.Auditable;
import com.example.codestates.comment.entity.Comment;
import com.example.codestates.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Band extends Auditable {

    @Id //엔티티의 기본키임을 명시함.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키의 자동생성을 지정하는 어노베이션.
    private Long bandId;

    @Column(length = 100, nullable = false)
    private String school; //학교명

    @Column(length = 100, nullable = false)
    private String schoolCode; //학교등급

    @Column(nullable = false)
    private int grade; //학년

    @Column(nullable = false)
    private int banNumber; //반

    @Column(length = 300, nullable = false)
    private String joinPass; // 반 가입 신청에 관련한 비밀번호

//    @Column(length = 100, nullable = false)
//    private String userName; // 신청자 성명

//    @OneToMany(mappedBy = "band", cascade = CascadeType.PERSIST)
//    private List<BandJoinList> bandJoinLists = new ArrayList<>();
//    public void addBandJoinList(BandJoinList bandJoinList){
//        this.bandJoinLists.add(bandJoinList);
//        if(bandJoinList.getBand() != this){
//            bandJoinList.addBandJoinList(this);
//        }
//    }
    @OneToMany(mappedBy = "band",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();
    public void addMember(Member member){
        this.members.add(member);
        if(member.getBand() !=this){
            member.addMember(this);
        }
    }
    @OneToMany(mappedBy="band", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<Comment> comments =new ArrayList<>();
    public void addComment(Comment comment) {
        this.comments.add(comment);
        if (comment.getBand() != this) {
            comment.addComment(this);
        }
    }


    //반 신청 상태에 대한 코드
    @Enumerated(value = EnumType.STRING)
    @Column(length = 200, nullable = false)
    private StatusUpdate statusUpdate = StatusUpdate.SignUp; //기본값으로 신청완료를 나타냄.


    public enum StatusUpdate  {

        ApplicationCompleted("신청완료"), //신청완료
        ApplicationPending("개설중"), //개설중
        SignUp("가입하기"); // 가입하기

        @Getter
        private String status;
        StatusUpdate(String status) {
            this.status = status;
        }


    }


    public Band(String school, String schoolCode, int grade, int banNumber, String joinPass, StatusUpdate statusUpdate){

        this.school = school;
        this.schoolCode = schoolCode;
        this.grade = grade;
        this.banNumber = banNumber;
        this.joinPass = joinPass;
        //this.userName = userName;
        //this.member = member;
        this.statusUpdate = statusUpdate;


    }//더미데이터 생성관련


}
