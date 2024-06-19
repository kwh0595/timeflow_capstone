package com.capstone.timeflow.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
//openai의 gpt에게 요청을 보낼 때 사용하는 객체 dto
@Data
public class ChatBotRequest {
    private String model;
    private List<GptMessage> gptMessages;
    //사용자의 입력은 message라는 객체로 변환되어 리스트에 추가됨
    public ChatBotRequest(String model, String prompt) {
        this.model = model;
        this.gptMessages =  new ArrayList<>();
        this.gptMessages.add(new GptMessage("user", prompt));
    }
}