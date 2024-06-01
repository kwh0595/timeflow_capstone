package com.capstone.timeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 가지는 생성자
public class UserDTO {
    private Long userId;
    private String userName;
    private String userMail;
    private String userPassword; //비밀번호 사용 시 해싱처리 필요.
    private LocalDate userBirth;
    private String userSex;
    private Integer userAge;
    private LocalDateTime userJoinDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(LocalDate userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public LocalDateTime getUserJoinDate() {
        return userJoinDate;
    }

    public void setUserJoinDate(LocalDateTime userJoinDate) {
        this.userJoinDate = userJoinDate;
    }
}
