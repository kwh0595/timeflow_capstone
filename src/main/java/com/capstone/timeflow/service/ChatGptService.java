package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ChatGPTRequest;
import com.capstone.timeflow.dto.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ChatGptService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${gpt_api_url}")
    private String apiURL; // 실제 API 엔드포인트로 변경
    @Value("${gpt_model}")
    private String model = "gpt-3.5-turbo"; // 실제 모델명으로 변경

    public ChatGPTResponse chat(String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request);

        ResponseEntity<ChatGPTResponse> response = restTemplate.postForEntity(apiURL, entity, ChatGPTResponse.class);
        return response.getBody();
    }
}
