package com.capstone.timeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 가지는 생성자
public class JoinDTO {
    private Long id;
    private Long teamId; // Join Entity에서 Team 객체 대신 Team의 ID를 사용
    private String role;
    private String teamColor;
}
