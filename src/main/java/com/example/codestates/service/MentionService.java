package com.example.codestates.service;

import com.example.codestates.dto.MentionDto;
import com.example.codestates.entity.Mention;
import com.example.codestates.entity.User;
import com.example.codestates.repository.MentionRepository;
import com.example.codestates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MentionService {
    private final MentionRepository mentionRepository;
    private final UserRepository userRepository;

    @Transactional
    public MentionDto write(MentionDto mentionDto) {
        User receiver = userRepository.findById(mentionDto.getReceiveUserId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        User sender = userRepository.findById(mentionDto.getSenderUserId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        Mention mention = new Mention();
        mention.setReceiveUserId(receiver);
        mention.setSenderUserId(sender);
        mention.setSendContent(mentionDto.getSendContent());
        mention.setDeletedByReceiver(false);
        mention.setDeletedBySender(false);
        mentionRepository.save(mention);

        return MentionDto.toDto(mention);
    }

//    @Transactional
//    public MentionDto write(MentionDto mentionDto) {
//        User receiverId = userRepository.findById(mentionDto.getReceiveUserId());
//        User senderId = userRepository.findById(mentionDto.getSenderUserId());
//
//        Mention mention = new Mention();
//        mention.setReceiveUserId(receiverId);
//        mention.setSenderUserId(senderId);
//
//        mention.setId(mentionDto.getId());
//        mention.setSendContent(mentionDto.getSendContent());
//        mention.setDeletedByReceiver(false);
//        mention.setDeletedBySender(false);
//        mentionRepository.save(mention);
//
//        return MentionDto.toDto(mention);
//    }

    @Transactional(readOnly = true)
    public MentionDto findMentionById(User user) {
        Mention mention = mentionRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        return MentionDto.toDto(mention);
    }

    @Transactional(readOnly = true)
    public List<MentionDto> receivedMentions(User user) {
        // 받은 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        // 추후 JWT를 이용해서 재구현 예정
        List<Mention> mentions = mentionRepository.findBySenderUserId(user);
        List<MentionDto> mentionDtos = new ArrayList<>();

        for (Mention mention : mentions) {
            // mention 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if (!mention.isDeletedByReceiver()) {
                mentionDtos.add(MentionDto.toDto(mention));
            }
        }
        return mentionDtos;
    }

    // 받은 편지 삭제
    @Transactional
    public Object deleteMentionByReceiver(MentionDto mentionDto, User user) {

        Mention mention = mentionRepository.findById(mentionDto.getId()).get();
        mention.deleteByReceiver(); // 받은 사람에게 메시지 삭제
        if (mention.isDeleted()) {
            // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
            mentionRepository.delete(mention);
            return "양쪽 모두 삭제";
        }
        return "한쪽만 삭제";
    }


    @Transactional(readOnly = true)
    public List<MentionDto> sendMention(User user) {
        // 보낸 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        // 추후 JWT를 이용해서 재구현 예정
        List<Mention> mentions = MentionRepository.findAllBySenderId(user);
        List<MentionDto> mentionDtos = new ArrayList<>();

        for (Mention mention : mentions) {
            // mention 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if (!mention.isDeletedBySender()) {
                mentionDtos.add(MentionDto.toDto(mention));
            }
        }
        return mentionDtos;
    }

    // 보낸 편지 삭제
    @Transactional
    public Object deleteMentionBySender(MentionDto mentionDto, User user) {
        Mention mention = mentionRepository.findById(mentionDto.getId()).get();
        mention.deleteBySender(); // 받은 사람에게 메시지 삭제
        if (mention.isDeleted()) {
            // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
            mentionRepository.delete(mention);
            return "양쪽 모두 삭제";
        }
        return "한쪽만 삭제";
    }
}