package com.example.codestates.member.entity;

import com.example.codestates.band.entity.Band;
//import com.example.codestates.band.entity.BandJoinList;
import com.example.codestates.bgcolor.entity.BgColor;
import com.example.codestates.comment.entity.Comment;
import com.example.codestates.mbti.entity.Mbti;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, length = 6, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    // (2) 추가
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
//    @Column(nullable = false)
//    private String role = "USER";

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "bgcolor_id")
    private BgColor bgColor;    //닉네임 배경색
    public void setBgColor (BgColor bgColor){
        this.bgColor = bgColor;
    }
    public void addMember(BgColor bgColor){
        this.bgColor =bgColor;
        if(this.bgColor.getMembers().contains(this)){
            this.bgColor.addMember(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;
    public void setMbti (Mbti mbti){
        this.mbti =mbti;
    }
    public void addMember(Mbti mbti){
        this.mbti =mbti;
        if(this.mbti.getMembers().contains(this)){
            this.mbti.addMember(this);
        }

    }
    @ManyToOne
    @JoinColumn(name ="band_id")
    private Band band;
    public void setBand (Band band){
        this.band =band;
    }
    public void addMember(Band band){
        this.band = band;
        if(this.band.getMembers().contains(this)){
            this.band.addMember(this);
        }
    }
//    @OneToMany(mappedBy = "member",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
//    private List<BandJoinList> bandJoinLists = new ArrayList<>();
//
//    public void addBandJoinList(BandJoinList bandJoinList){
//        this.bandJoinLists.add(bandJoinList);
//        if(bandJoinList.getMember() != this){
//            bandJoinList.addBandJoinList(this);
//        }
//    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        this.comments.add(comment);
        if(comment.getMember() != this){
            comment.addComment(this);
        }
    }

}
