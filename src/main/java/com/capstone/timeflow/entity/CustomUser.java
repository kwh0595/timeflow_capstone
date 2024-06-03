package com.capstone.timeflow.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class CustomUser extends User {
    private UserEntity userEntity;

    public CustomUser(UserEntity userEntity){
        super(userEntity.getUserMail(), userEntity.getUserPassword(), AuthorityUtils.createAuthorityList(userEntity.getRole().toString()));
        this.userEntity = userEntity;
    }
    public String getUserName() {
        return this.userEntity.getUserName();
    }
}
