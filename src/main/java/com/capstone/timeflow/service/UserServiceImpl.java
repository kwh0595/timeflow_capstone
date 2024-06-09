package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.UserDTO;
import com.capstone.timeflow.entity.UserEntity;

import com.capstone.timeflow.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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
    public UserEntity updateUser(String name, UserDTO userDTO) {
        UserEntity user = userRepository.findByUserName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserName(userDTO.getUserName());
        return userRepository.save(user);
    }
    public boolean isPasswordCorrect(String username, String password) {
        Optional<UserEntity> user = userRepository.findByUserName(username);
        if (user.isPresent()) {
            //해싱된 비밀번호와 입력된 비밀번호를 비교
            return bCryptPasswordEncoder.matches(password, user.get().getUserPassword());
        }
        return false;
    }
    @Override
    public boolean updatePassword(String username, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findByUserName(username);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setUserPassword(bCryptPasswordEncoder.encode(newPassword)); // 비밀번호 해시화
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
