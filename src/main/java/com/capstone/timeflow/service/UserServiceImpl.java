package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.UserDTO;
import com.capstone.timeflow.entity.UserEntity;

import com.capstone.timeflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private  final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


   // 회원가입 메서드
    public void save(UserDTO userDTO){

           UserEntity userEntity = UserEntity.toUserEntity(userDTO);

           //비밀번호 암호화
           String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getUserPassword());
           userEntity.setUserPassword(encodedPassword);//암호화된 비밀번호를 엔티티에 저장
           userRepository.save(userEntity); //사용자 레포지토리에 암호화된 비밀번호 저장
    }
}
