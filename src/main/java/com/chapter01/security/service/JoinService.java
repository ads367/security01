package com.chapter01.security.service;

import com.chapter01.security.dto.JoinDTO;
import com.chapter01.security.entity.UserEntity;
import com.chapter01.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinPorc(JoinDTO dto) {
        // 아이디 중복 검사
        boolean isUser = userRepository.existsByUsername(dto.getUsername());
        if(isUser) {
            return;
        }

        // 가입 내용 입력
        UserEntity data = new UserEntity();
        data.setUsername(dto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}