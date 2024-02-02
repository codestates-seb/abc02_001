package com.example.codestates.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class CommentDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotNull
        private String context;
        private String nickname;
    }
    @Getter
    public static class Patch {
        private Long commentId;
        private String context;
        private String nickname;

        public void setCommentId(long commentId) {
            this.commentId =commentId;
        }
    }

}
