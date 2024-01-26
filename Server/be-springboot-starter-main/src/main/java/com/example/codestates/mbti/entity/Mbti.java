package com.example.codestates.mbti.entity;

import com.example.codestates.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mbti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mbtiId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MbtiType type;

    @Column(nullable = false)
    private String mbtiColor; //색상값 가져와서 적용해야하는데 방법 알아봐야함

    @OneToMany(mappedBy = "mbti",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();
    public void addMember(Member member){
        this.members.add(member);
        if(member.getMbti() != this){
            member.addMember(this);
        }
    }
    public enum MbtiType {
        ISTJ("ISTJ"), ISFJ("ISFJ"), INFJ("INFJ"), INTJ("INTJ"),
        ISTP("ISTP"), ISFP("ISFP"), INFP("INFP"), INTP("INTP"),
        ESTP("ESTP"), ESFP("ESFP"), ENFP("ENFP"), ENTP("ENTP"),
        ESTJ("ESTJ"), ESFJ("ESFJ"), ENFJ("ENFJ"), ENTJ("ENTJ");

        private final String mbti;

        MbtiType(String mbti) {
            this.mbti = mbti;
        }

        public String getMbti() {
            return mbti;
        }
    }

}
