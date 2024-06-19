package com.capstone.timeflow.service;

import com.capstone.timeflow.dto.ChatBotResponse;
import com.capstone.timeflow.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatBotService {

    @Autowired
    private ScheduleServiceImpl scheduleService;

    // OpenAI API 키를 application.properties에서 주입받습니다.
    @Value("${gpt_api_key}")
    private String apiKey;

    // OpenAI API의 엔드포인트 URL
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    // 채팅방별 상태를 저장하는 맵. 채팅방 ID를 키로 사용합니다.
    private final Map<String, String> roomState = new ConcurrentHashMap<>();

    public ChatBotResponse sendMessage(Long teamId, String prompt, Long userId) {
        if (prompt.startsWith("#검색")) {
            System.out.println("#검색 시작");
            return handleSearch(teamId, prompt.substring(3).trim());
        } else if (prompt.startsWith("#일정등록")) {
            System.out.println("#일정 등록 시작");
            return handleSchedule(teamId, prompt.substring(5).trim(), userId);
        } else {
            return sendToOpenAI(prompt);
        }
    }

    private ChatBotResponse handleSearch(Long teamId, String query) {
        ChatBotResponse gptResponse = sendToOpenAI(query);

        if (gptResponse == null || gptResponse.getChoices() == null || gptResponse.getChoices().length == 0) {
            throw new RuntimeException("GPT-3 응답이 유효하지 않습니다.");
        }

        // GPT-3의 응답에서 필요한 정보를 추출
        String searchResult = gptResponse.getChoices()[0].getMessage().getContent().trim();
        System.out.println(searchResult);

        // 응답 생성
        ChatBotResponse response = new ChatBotResponse();
        ChatBotResponse.Message message = new ChatBotResponse.Message();
        message.setRole("assistant");
        message.setContent("채팅방 " + teamId + "의 검색 결과: " + searchResult);
        response.setChoices(new ChatBotResponse.Choice[]{new ChatBotResponse.Choice(message)});
        System.out.println(response);

        return response;
    }

    /**
     * 일정 등록 명령어를 처리합니다.
     */
    private ChatBotResponse handleSchedule(Long teamId, String details, Long userId) {
        String prompt = "다음 텍스트에서 title(String), content(String), startDate(LocalDate YYYY-MM-DD), endDate(LocalDate YYYY-MM-DD), sprocess(완료, 진행중, 진행예정), assigneeUsernames(String)을 추출해줘. 만약 시간이 지정되어있지 않다면 현재 시각을 기준으로 등록 해줘. 각 필드를 콜론(:)으로 구분하고, 줄바꿈으로 구분하세요.:\n" + details;
        ChatBotResponse gptResponse = sendToOpenAI(prompt);

        if (gptResponse == null || gptResponse.getChoices() == null || gptResponse.getChoices().length == 0) {
            throw new RuntimeException("GPT-3 응답이 유효하지 않습니다.");
        }

        // GPT-3의 응답에서 필요한 정보를 추출
        String extractedInfo = gptResponse.getChoices()[0].getMessage().getContent().trim();
        System.out.println("extractInfo : 0" + extractedInfo);
        ScheduleDTO scheduleDTO = parseScheduleInfo(extractedInfo);

        scheduleService.createTeamSchedule(scheduleDTO, teamId, userId);
        System.out.println("등록 완료 DB 확인해보셈 ㅋㅋ");

        // 응답 생성
        ChatBotResponse response = new ChatBotResponse();
        ChatBotResponse.Message message = new ChatBotResponse.Message();
        message.setRole("assistant");
        message.setContent("요청하신 일정이 등록 완료 되었습니다.");
        response.setChoices(new ChatBotResponse.Choice[]{new ChatBotResponse.Choice(message)});
        System.out.println(response);
        return response;
    }


    private ChatBotResponse sendToOpenAI(String prompt) {
        System.out.println("GPT한테 보낼거임 ㅋㅋ");
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 요청 본문 설정
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo"); // 모델 파라미터 추가
        body.put("messages", new Object[]{
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", prompt);
                }}
        });
        body.put("max_tokens", 150);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            // OpenAI API에 POST 요청을 보내고 응답을 받습니다.
            ChatBotResponse response = restTemplate.postForObject(API_URL, request, ChatBotResponse.class);
            if (response != null && response.getChoices() != null && response.getChoices().length > 0) {
                System.out.println("Response: " + response.getChoices()[0].getMessage().getContent());
            } else {
                System.out.println("No response or empty choices");
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ScheduleDTO parseScheduleInfo(String extractedInfo) {
        System.out.println("ScheduleDTO extractInfo : " + extractedInfo);

        // GPT-3의 응답을 ScheduleDTO로 변환하는 로직
        String[] infoParts = extractedInfo.split("\n"); // 줄바꿈 기준으로 분리
        String title = null;
        String content = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        String sprocess = null;
        List<String> assigneeUsernames = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (String part : infoParts) {
            String[] keyValue = part.split(": ");
            if (keyValue.length < 2) continue; // 잘못된 형식 무시
            String key = keyValue[0].trim(); // 키 부분
            String value = keyValue[1].trim(); // 값 부분

            switch (key) {
                case "title":
                    title = value;
                    break;
                case "content":
                    content = value;
                    break;
                case "startDate":
                    if (value.equals("현재 시각")) {
                        startDate = LocalDate.now(); // 현재 시각 설정
                    } else {
                        startDate = LocalDate.parse(value, formatter); // 주어진 날짜 파싱
                    }
                    break;
                case "endDate":
                    if (value.equals("현재 시각으로부터 한달 뒤")) {
                        endDate = LocalDate.now().plusMonths(1);
                    } else if (value.equals("없음")) {
                        endDate = null; // '없음'일 경우 null로 설정
                    } else {
                        endDate = LocalDate.parse(value, formatter); // 주어진 날짜 파싱
                    }
                    break;
                case "sprocess":
                    sprocess = value;
                    break;
                case "assigneeUsernames":
                    if (!value.equals("null")) {
                        // 값이 'null'이 아닐 경우, 콤마로 구분된 사용자 이름들을 리스트에 추가
                        for (String username : value.split(",")) {
                            assigneeUsernames.add(username.trim());
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected key: " + key); // 예기치 않은 키가 있을 경우 예외 발생
            }
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO(null, title, content, startDate, endDate, sprocess, "blue", assigneeUsernames);
        System.out.println("return ScheduleDTO : \n" + scheduleDTO);
        return scheduleDTO;
    }

}
