package com.capstone.timeflow.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//사용자의 입력과 모델의 응답을 관리하기 위한 객체
// 1. 사용자의 입력을 message 객체로 변환하여 요청을 구성
// 2. 모델의 응답을 message 객체로 받아 처리
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GptMessage {
    private String role;
    private String content;
}