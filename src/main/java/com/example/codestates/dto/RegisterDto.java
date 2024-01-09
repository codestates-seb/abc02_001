package com.example.codestates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private Long id;
    private String nickname;
    private String styleCode;
    private String password;
    private String email;
    private String mbti;
}
