package com.capstone.timeflow.controller;


import com.capstone.timeflow.dto.ChatGPTRequest;
import com.capstone.timeflow.dto.ChatGPTResponse;
import com.capstone.timeflow.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class GptController {

    private final String apiURL = "https://api.openai.com/v1/chat/completions";
    private final String model = "gpt-3.5-turbo";

    //http 클라이언트 -> restful 웹 서비스와 통신하는데 사용

    @Autowired
    private ChatGptService chatGptService;

    //localhost:8082/gpt-chat으로 요청이 들어올 때 실행
    // 1. 클라이언트로 부터 prompt를 받아 chatgptrequest를 생성하여 openai gpt에게 post 요청을 보냄
    // 2. 응답으로 chatgptresponse 객체를 받아 반환함
    @GetMapping("/gpt-chat")
    public ChatGPTResponse chat(@RequestParam String prompt) {

        return chatGptService.chat(prompt);
    }
}
