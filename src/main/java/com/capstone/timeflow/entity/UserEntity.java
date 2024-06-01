package com.capstone.timeflow.entity;

import com.capstone.timeflow.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Getter
@Setter
@Builder
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Long userId;

    @Column(name="userName",nullable = false,length=35)
    private String userName;

    @Column(name="userMail",length=50)
    private String userMail;     //사용자 아이디

    @Column(name="userPassword", nullable = false,length =100)
    private String userPassword;    //사용자 비밀번호 해싱 과정 필요

    @Column(name="userBirth",nullable = false)
    private LocalDate userBirth;

    @Column(name="userSex",nullable = false, length=25)
    private String userSex;

    @Column(name="userAge",columnDefinition = "int default 20")
    private Integer userAge;

    @Column(name = "userJoinDate")
    private LocalDateTime userJoinDate = LocalDateTime.now(); // 현재 시간으로 초기화

    @ManyToOne
    private TeamEntity teamEntity;

    public static UserEntity toUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity(); //userEntity 객체 생성
        userEntity.setUserName(userDTO.getUserName()); //이름
        userEntity.setUserMail(userDTO.getUserMail()); // 메일
        userEntity.setUserPassword(userDTO.getUserPassword()); //비밀번호
        userEntity.setUserBirth(userDTO.getUserBirth());    //생년월일
        // 가입날짜가 null이면 현재 시간을 사용
        if (userDTO.getUserJoinDate() == null) {
            userEntity.setUserJoinDate(LocalDateTime.now());
        } else {
            userEntity.setUserJoinDate(userDTO.getUserJoinDate());
        }
        userEntity.setUserSex(userDTO.getUserSex()); //성별
        // 생년월일을 통해 나이 계산
        LocalDate today = LocalDate.now(); // 현재 날짜
        LocalDate birthDate = userDTO.getUserBirth(); // 사용자의 생년월일
        if (birthDate != null) {
            int age = Period.between(birthDate, today).getYears();
            userEntity.setUserAge(age);
        }
        return userEntity;
    }
}

