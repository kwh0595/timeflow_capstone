package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.LoginRequest;
import com.capstone.timeflow.dto.UserDTO;
import com.capstone.timeflow.entity.UserEntity;

import com.capstone.timeflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    /**
     *  로그인 기능
     *  화면에서 LoginRequest(loginId, password)을 입력받아 loginId와 password가 일치하면 User return
     *  loginId가 존재하지 않거나 password가 일치하지 않으면 null return
     */
    public UserEntity login(LoginRequest req) {
        Optional<UserEntity> optionalUser = userRepository.findByUserMail(req.getUserMail());

        // loginId와 일치하는 User가 없으면 null return
        if(optionalUser.isEmpty()) {
            return null;
        }

        UserEntity user = optionalUser.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!bCryptPasswordEncoder.matches(req.getUserPassword(), user.getUserPassword())) {
            return null; // 비밀번호가 일치하지 않으면 null 반환
        }
        System.out.println("비밀번호 일치!");
        return user;
    }
    public UserEntity getLoginUserById(Long userId) {
        if(userId == null) return null;

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

}
