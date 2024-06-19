package com.capstone.timeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponse {
    //응답으로 받은 여러개의 선택지를 저장하는 리스트
    private List<Choice> choices;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        //선택지의 인덱스와 모델이 생성한 메세지 저장
        private int index;
        private GptMessage gptMessage;

    }
}