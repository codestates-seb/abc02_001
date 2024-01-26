package com.example.codestates.comment.controller;

import com.example.codestates.band.entity.Band;
import com.example.codestates.comment.dto.CommentDto;
import com.example.codestates.comment.entity.Comment;
import com.example.codestates.comment.mapper.CommentMapper;
import com.example.codestates.comment.service.CommentService;
import com.example.codestates.exception.BusinessLogicException;
import com.example.codestates.exception.ExceptionCode;
import com.example.codestates.response.MultiResponseDto;
import com.example.codestates.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final static String COMMENT_DEFAULT_URL = "/comments";
    private final CommentService commentService;
    private final CommentMapper mapper;


    public CommentController( CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }


    @PostMapping(params = {"band_id"})
    public ResponseEntity<Comment> postComment(@RequestParam("band_id") long bandId,@RequestBody CommentDto.Post requestBody) {
            Comment comment = commentService.createComment(bandId,mapper.commentPostDtoTocomment(requestBody));
            URI location = UriCreator.createUri(COMMENT_DEFAULT_URL, comment.getCommentId());

            return ResponseEntity.created(location).build();

    }

    @GetMapping(params = {"band_id"})
    public ResponseEntity getComment(@RequestParam("band_id") long bandId,
                                     @Positive @RequestParam int page,
                                     @Positive @RequestParam int size){

        Page<Comment> foundComment = commentService.findComments(bandId, page-1,size);
        List<Comment> comments = foundComment.getContent();
        return ResponseEntity.ok(new MultiResponseDto(mapper.commentsToCommentResponseDtos(comments), foundComment));
    }
    @DeleteMapping(params = {"band_id"},value = "/{comment_id}")
    public ResponseEntity deleteComment(@RequestParam("band_id") long bandId, @PathVariable("comment_id") long commentId){
        commentService.deleteComment(bandId,commentId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping(params = {"band_id"},value = "/{comment_id}")
    public ResponseEntity patchComment(@RequestParam("band_id") long bandId,
                                       @PathVariable("comment_id") long commentId,
                                      @RequestBody CommentDto.Patch requestBody ){
        String patchNickname = requestBody.getNickname();

        Comment updateComment = commentService.updateComment(bandId,commentId, mapper.commentPatchDtoTocomment(requestBody));
        return ResponseEntity.ok(mapper.commentToCommentResponseDto(updateComment));
    }
}
