package com.capstone.timeflow.controller;


import com.capstone.timeflow.dto.ChatGPTRequest;
import com.capstone.timeflow.dto.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



//
@RestController
public class GptController {

    private final String apiURL = "https://api.openai.com/v1/chat/completions";
    private final String model = "gpt-3.5-turbo"; // Set the model name

    private final RestTemplate restTemplate;

    @Autowired
    public GptController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/gpt-chat")
    public ChatGPTResponse chat(@RequestParam(name = "prompt") String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = restTemplate.postForObject(apiURL, request, ChatGPTResponse.class);
        System.out.println("gptcontroller 실행 : "+chatGPTResponse);
        return chatGPTResponse;
    }

}
