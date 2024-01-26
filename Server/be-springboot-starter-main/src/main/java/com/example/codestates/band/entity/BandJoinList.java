//package com.example.codestates.band.entity;
//
//
//import com.example.codestates.audit.Auditable;
//import com.example.codestates.member.entity.Member;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//public class BandJoinList extends Auditable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long bandJoinListId;
//
//    @ManyToOne
//    @JoinColumn(name = "member_Id")
//    private Member member;
//    public void setMember(Member member){
//        this.member =member;
//    }
//
//    public void addBandJoinList(Member member){
//        this.member=member;
//        if(this.member.getBandJoinLists().contains(this)){
//            this.member.addBandJoinList(this);
//        }
//    }
//    @ManyToOne
//    @JoinColumn(name = "band_Id")
//    private Band band;
//    public void setBand(Band band){
//        this.band = band;
//    }
//    public void addBandJoinList(Band band){
//        this.band=band;
//        if(this.band.getBandJoinLists().contains(this)){
//            this.band.addBandJoinList(this);
//        }
//    }
//
//
//}
