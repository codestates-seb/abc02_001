package com.example.codestates.comment.service;

import com.example.codestates.band.entity.Band;
import com.example.codestates.band.repository.BandRepository;
import com.example.codestates.comment.entity.Comment;
import com.example.codestates.comment.repository.CommentRepository;
import com.example.codestates.exception.BusinessLogicException;
import com.example.codestates.exception.ExceptionCode;
import com.example.codestates.member.entity.Member;
import com.example.codestates.member.repository.MemberRepository;
import com.example.codestates.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CustomBeanUtils<Comment> beanUtils;
    private final BandRepository bandRepository;
    private final MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository, CustomBeanUtils<Comment> beanUtils, BandRepository bandRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.beanUtils = beanUtils;

        this.bandRepository = bandRepository;
        this.memberRepository = memberRepository;
    }

    public Comment createComment(long bandId, Comment comment) {
        Band band= bandRepository.findById(bandId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BAND_NOT_FOUND));
        comment.setBand(band);
        Member member = memberRepository.findByNickname(comment.getNickname()).orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        comment.setMember(member);
        return commentRepository.save(comment);

    }

    public Comment updateComment(long bandId,long commentId ,Comment comment) {
        bandRepository.findById(bandId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BAND_NOT_FOUND));
        Comment foundComment = findComment(commentId);
        if(foundComment.getNickname() !=null && foundComment.getNickname() != comment.getNickname()){
           throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_COMMENT);
        }
       else{ Comment updatedComment = beanUtils.copyNonNullProperties(comment, foundComment);
        return commentRepository.save(updatedComment);}
    }

    @Transactional(readOnly = true)
    public Comment findComment(long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }
    @Transactional(readOnly = true)
    public Page<Comment> findComments(long bandId, int page, int size) {
        bandRepository.findById(bandId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BAND_NOT_FOUND));

        return commentRepository.findAll(PageRequest.of(page, size,Sort.by("createdAt").descending()));
    }

    public void deleteComment(long bandId,long commentId) {
        bandRepository.findById(bandId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BAND_NOT_FOUND));
        Comment foundComment = findComment(commentId);
        commentRepository.delete(foundComment);
    }
}
