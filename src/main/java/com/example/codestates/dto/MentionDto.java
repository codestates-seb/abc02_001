package com.example.codestates.dto;

import com.example.codestates.entity.Mention;
import com.example.codestates.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentionDto {
    private Long id;
    private String sendContent;
    private Long senderUserId;
    private Long receiveUserId;


    public static MentionDto toDto(Mention mention) {
        return new MentionDto(
                mention.getId(),
                mention.getSendContent(),
                mention.getSenderUserId().getId(),
                mention.getReceiveUserId().getId()
        );
    }
}