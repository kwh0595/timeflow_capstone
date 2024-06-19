package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ChatBotResponse;
import com.capstone.timeflow.dto.ChatGPTResponse;
import com.capstone.timeflow.dto.ScheduleDTO;
import com.capstone.timeflow.entity.ScheduleEntity;
import com.capstone.timeflow.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatBotService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    // OpenAI API 키를 application.properties에서 주입받습니다.
    @Value("${gpt_api_key}")
    private String apiKey;

    // OpenAI API의 엔드포인트 URL
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    // 채팅방별 상태를 저장하는 맵. 채팅방 ID를 키로 사용합니다.
    private final Map<String, String> roomState = new ConcurrentHashMap<>();

    public ChatBotResponse sendMessage(Long teamId, String prompt) {
        if (prompt.startsWith("#검색")) {
            return handleSearch(teamId, prompt.substring(3).trim());
        } else if (prompt.startsWith("#일정등록")) {
            return handleSchedule(teamId, prompt.substring(5).trim());
        } else {
            return sendToOpenAI(prompt);
        }
    }

    private ChatBotResponse handleSearch(Long teamId, String query) {
        ChatBotResponse response = sendToOpenAI(query);
        response.setChoices(new ChatBotResponse.Choice[]{new ChatBotResponse.Choice("채팅방 " + teamId + "의 검색 결과: " + query)});
        return response;
    }

    /**
     * 일정 등록 명령어를 처리합니다.
     */
    private ChatBotResponse handleSchedule(Long teamId, String details) {
        String prompt = "다음 텍스트에서 일정 제목, 내용, 시작 날짜 및 시간, 종료 날짜 및 시간을 추출해줘:\n" + details;
        ChatBotResponse gptResponse = sendToOpenAI(prompt);

        // GPT-3의 응답에서 필요한 정보를 추출
        String extractedInfo = gptResponse.getChoices()[0].getText().trim();
        ScheduleDTO scheduleDTO = parseScheduleInfo(extractedInfo);

        ScheduleEntity scheduleEntity = scheduleService.createTeamSchedule(scheduleDTO, teamId);

        ChatBotResponse response = new ChatBotResponse();
        response.setChoices(new ChatBotResponse.Choice[]{new ChatBotResponse.Choice("채팅방 " + teamId + "의 일정 등록 완료: " + extractedInfo)});
        return response;
    }

    private ChatBotResponse sendToOpenAI(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 요청 본문 설정
        Map<String, Object> body = new HashMap<>();
        body.put("prompt", prompt);
        body.put("max_tokens", 150);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // OpenAI API에 POST 요청을 보내고 응답을 받습니다.
        return restTemplate.postForObject(API_URL, request, ChatBotResponse.class);
    }

    private ScheduleDTO parseScheduleInfo(String extractedInfo) {
        // GPT-3의 응답을 ScheduleDTO로 변환하는 로직
        String[] infoParts = extractedInfo.split(",");
        String title = infoParts[0].split(":")[1].trim();
        String content = infoParts[1].split(":")[1].trim();
        LocalDateTime startDate = LocalDateTime.parse(infoParts[2].split(":")[1].trim());
        LocalDateTime endDate = LocalDateTime.parse(infoParts[3].split(":")[1].trim());

        return new ScheduleDTO(null, title, content, startDate, endDate, null, null, null);
    }
}
