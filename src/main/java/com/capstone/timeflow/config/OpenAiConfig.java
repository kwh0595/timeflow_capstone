package com.capstone.timeflow.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//OPENAI GPT API에 접근하기 위한 기본적인 설정
@Configuration
public class OpenAiConfig {
    //.env에 있는 gpt_api_key 가져오기
    @Value("${gpt_api_key}")
    private String openAiKey;
    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            //OPENAI API에 요청을 보낼때마다 Authorization헤더에 Bearer + api_key를 포함시켜 보냄 -> 인증 과정
            request.getHeaders().add("Authorization", "Bearer " + openAiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}