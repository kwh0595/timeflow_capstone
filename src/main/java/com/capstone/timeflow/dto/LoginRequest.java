package com.capstone.timeflow.dto;

import com.capstone.timeflow.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    private String userMail;

    private String userPassword;
    public static LoginRequest toLoginRequest(UserEntity userEntity){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserMail(userEntity.getUserMail());
        loginRequest.setUserPassword(userEntity.getUserPassword());
        return loginRequest;
    }
}
