package com.example.codestates.member.controller;

import com.example.codestates.bgcolor.entity.BgColor;
import com.example.codestates.member.dto.MemberDto;
import com.example.codestates.member.entity.Member;
import com.example.codestates.member.mapper.MemberMapper;
import com.example.codestates.member.service.MemberService;
import com.example.codestates.response.MultiResponseDto;
import com.example.codestates.response.SingleResponseDto;
import com.example.codestates.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/users";
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    // 회원 가입 요청을 처리합니다.
    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody){
        Member member = mapper.memberPostDtoToMember(requestBody);
        Member createdMember = memberService.createMember(member);

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createdMember.getMemberId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = {"mbtitype"})
    public ResponseEntity getMbtiMembers(@RequestParam("mbtitype") String mbtitype,
                                     @Positive @RequestParam int page,
                                     @Positive @RequestParam int size){
       Page<Member> pageMbtiMembers= memberService.findMembers(mbtitype,page-1,size);
       List<Member> mbtiMembers = pageMbtiMembers.getContent();

        return ResponseEntity.ok(new MultiResponseDto(mapper.membersToMemberResponseDtos(mbtiMembers),pageMbtiMembers));
    }
    @GetMapping(params = {"interestingtype"})
    public ResponseEntity getInterestingMembers(@RequestParam("interestingtype")String interestingtype,
                                                @Positive @RequestParam int page,
                                                @Positive @RequestParam int size){
        Page<Member> pageInterestingMembers = memberService.findMembers(interestingtype,page-1,size);
        List<Member> interestingMembers = pageInterestingMembers.getContent();
        return ResponseEntity.ok(new MultiResponseDto(mapper.membersToMemberResponseDtos(interestingMembers),pageInterestingMembers));

    }
    @GetMapping
    public ResponseEntity getMemberinfo(){

        return null;
    }

    @PatchMapping(value = "/{member_id}")
    public ResponseEntity patchMember(@PathVariable("member_id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody){
        requestBody.setMemberId(memberId);
        Member member =
                memberService.updateMember(mapper.memberPatchDtoToMember(requestBody),memberId);
        return ResponseEntity.ok(new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)));
    }
    @DeleteMapping(value = "/{member_id}")
    public ResponseEntity deleteMember(@PathVariable("member_id")@Positive long memberId){
        memberService.deleteMember(memberId);

        return ResponseEntity.noContent().build();
    }

}
