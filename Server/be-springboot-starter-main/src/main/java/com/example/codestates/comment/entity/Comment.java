package com.example.codestates.comment.entity;


import com.example.codestates.audit.Auditable;
import com.example.codestates.band.entity.Band;
import com.example.codestates.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(length = 200)
    private String context;
    @Column(nullable = false, length = 20)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;
    public void setBand(Band band) {
        this.band = band;
    }
    public void addComment(Band band){
        this.band =band;
        if(this.band.getComments().contains(this)){
            this.band.addComment(this);
        }
    }

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;
    public void setMember(Member member) {
    this.member = member;
    }
    public void addComment(Member member){
        this.member =member;
        if(this.member.getComments().contains(this)){
            this.member.addComment(this);
        }
    }



}
