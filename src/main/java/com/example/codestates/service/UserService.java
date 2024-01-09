package com.example.codestates.service;

import com.example.codestates.dto.RegisterDto;
import com.example.codestates.entity.User;
import com.example.codestates.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
            ;

    public User register(RegisterDto registerDto) {
        User user = new User();
        user.setId(registerDto.getId());
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setId(registerDto.getId());
        user.setRoles("ROLE_USER");
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("User의 ID를 찾을 수 없습니다.");
        });
    }
}