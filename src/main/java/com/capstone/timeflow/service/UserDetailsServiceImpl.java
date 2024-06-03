package com.capstone.timeflow.service;

import com.capstone.timeflow.entity.CustomUser;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserMail(userMail);
        return userEntity.map(CustomUser::new).orElseThrow(()->new UsernameNotFoundException(userMail));
    }
}
