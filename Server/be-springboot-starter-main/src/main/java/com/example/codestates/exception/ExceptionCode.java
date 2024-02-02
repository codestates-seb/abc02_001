package com.example.codestates.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    COMMENT_CODE_EXISTS(409, "Comment Code exists"),
    BAND_NOT_FOUND(404, "BAND not found"),
    CANNOT_CHANGE_COMMENT(403, "Comment can not change"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    BAND_ALREADY_EXIST(409,"Band already exist" ); //추가

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }



}

