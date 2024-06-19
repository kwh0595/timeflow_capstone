package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.ChatBotResponse;
import com.capstone.timeflow.dto.ChatMessage;
import com.capstone.timeflow.entity.ChatEntity;
import com.capstone.timeflow.entity.CustomUser;
import com.capstone.timeflow.service.ChatBotService;
import com.capstone.timeflow.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final SimpMessagingTemplate sendingOperations;
    private final ChatBotService chatBotService;


    //채팅방 view 띄우고 model(teamId, chatList) -> 클라이언트에 전송 -> 클라이언트는 해당 값을 가지고 팀 이름과 채팅 기록을 화면에 보여주기
    //채팅 기록 클라이언트에 전송
    @GetMapping("/team/{teamId}")
    public String teamChat(@PathVariable(required = false) Long teamId, Model model, Authentication auth){
        List<ChatEntity> chatList = chatService.findAllChatByTeamId(teamId);
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        model.addAttribute("teamId",teamId);
        model.addAttribute("chatList", chatList);
        model.addAttribute("userName",customUser.getUserName());
        model.addAttribute("userId", customUser.getUserEntity().getUserId());
        return "chatingRoom";

    }
    //채팅방에 메세지 보내기
    @MessageMapping("/{teamId}") // 클라이언트에서 해당 경로로 메시지 요청이 오면, 입장메시지와 일반메시지를 구분하여 브로드캐스트
    public void handleMessage(@DestinationVariable Long teamId, ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
            sendingOperations.convertAndSend("/team/" + teamId, message);
        } else {

            ChatEntity chat = chatService.createChat(teamId, message.getSender(), message.getMessage());
            ChatMessage chatMessage = ChatMessage.builder()
                    .teamId(teamId)
                    .sender(chat.getSender())
                    .userId(message.getUserId())
                    .message(chat.getMessage())
                    .build();
            sendingOperations.convertAndSend("/team/" + teamId, chatMessage);

            Long userId = message.getUserId();
            //Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
            System.out.println("sendUserID : "+ userId);

            try {
                //#으로 검색하고 서비스에서 #검색, #일정추가를 나눔. 만약 각각 나눠버리면 컨트롤러 코드가 너무 길어지고 중복코드 많아짐.
                //컨트롤러 역할은 딱 챗봇 응답이 필요한지 아닌지로 나누는게 나을듯.
                //그리고 사용자가 오타로 # 누를수도 있으니까 #검색, #일정추가가 아닌걸로 들어오면 봇이 저 두개만 사용할 수 있다고 출력해주면 될듯( sendTo()메서드 수정 )
                if (message.getMessage().startsWith("#")) {
                    ChatBotResponse botResponse = chatBotService.sendMessage(teamId, message.getMessage(), userId);
                    ChatMessage botMessage = ChatMessage.builder()
                            .teamId(teamId)
                            .sender("ChatBot")
                            .message(botResponse.getChoices()[0].getText())
                            .build();
                    System.out.println("Sending bot message: " + botMessage);
                    //챗봇은 클라이언트에서 전송버튼으로 받아오는 sender가 없으니 dto에 먼저 sender를 저장하고 db에 저장하는 방식
                    chatService.createChat(teamId, botMessage.getSender(), botMessage.getMessage());
                    sendingOperations.convertAndSend("/team/" + teamId, botMessage);
                }
                //사용자끼리의 일반 채팅
                else {
                    /*ChatEntity chat = chatService.createChat(teamId, message.getSender(), message.getMessage());
                    ChatMessage chatMessage = ChatMessage.builder()
                            .teamId(teamId)
                            .sender(chat.getSender())
                            .message(chat.getMessage())
                            .build();
                    sendingOperations.convertAndSend("/team/" + teamId, chatMessage);*/
                }
            } catch (Exception e) {
                // 예외 처리
                e.printStackTrace();
                // 필요에 따라 클라이언트에 에러 메시지를 전송할 수 있습니다.
            }
        }
    }
}