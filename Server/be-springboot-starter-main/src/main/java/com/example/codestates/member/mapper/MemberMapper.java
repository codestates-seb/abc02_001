package com.example.codestates.member.mapper;

import com.example.codestates.member.dto.MemberDto;
import com.example.codestates.member.dto.MemberResponseDto;
import com.example.codestates.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post requestBody);
    Member memberPatchDtoToMember(MemberDto.Patch requestBody);

    MemberResponseDto memberToMemberResponseDto(Member Member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> Members);

}
