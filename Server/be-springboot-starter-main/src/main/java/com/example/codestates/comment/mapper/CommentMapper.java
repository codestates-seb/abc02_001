package com.example.codestates.comment.mapper;

import com.example.codestates.comment.dto.CommentDto;
import com.example.codestates.comment.dto.CommentResponseDto;
import com.example.codestates.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    Comment commentPostDtoTocomment(CommentDto.Post requestBody);
    Comment commentPatchDtoTocomment(CommentDto.Patch requestBody);

    CommentResponseDto commentToCommentResponseDto(Comment comment);
    List<CommentResponseDto> commentsToCommentResponseDtos(List<Comment> comments);
}
