package com.capstone.timeflow.controller;
import com.capstone.timeflow.dto.ChatMessage;
import com.capstone.timeflow.entity.ChatEntity;
import com.capstone.timeflow.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    @Autowired
    private final ChatService chatService;
    private final SimpMessageSendingOperations sendingOperations;


    //채팅방 view 띄우고 model(teamId, chatList) -> 클라이언트에 전송 -> 클라이언트는 해당 값을 가지고 팀 이름과 채팅 기록을 화면에 보여주기
    //채팅 기록 클라이언트에 전송
    @GetMapping("/team/{teamId}")
    public String teamChat(@PathVariable(required = false) Long teamId, Model model, Principal principal){
        List<ChatEntity> chatList = chatService.findAllChatByTeamId(teamId);
        model.addAttribute("teamId",teamId);
        model.addAttribute("chatList", chatList);
        model.addAttribute("userName",principal.getName());
        return "chatingRoom";

    }
    //채팅방에 메세지 보내기
    @MessageMapping("/{teamId}") // 클라이언트에서 해당 경로로 메시지 요청이 오면, 입장메시지와 일반메시지를 구분하여 브로드캐스트
    public void handleMessage(@DestinationVariable Long teamId, ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
            sendingOperations.convertAndSend("/team/" + teamId, message);
        } else {
            try {
                // 일반 메시지 처리
                ChatEntity chat = chatService.createChat(teamId, message.getSender(), message.getMessage());
                ChatMessage chatMessage = ChatMessage.builder()
                        .teamId(teamId)
                        .sender(chat.getSender())
                        .message(chat.getMessage())
                        .build();
                sendingOperations.convertAndSend("/team/" + teamId, chatMessage);
            } catch (Exception e) {
                // 예외 처리
                e.printStackTrace();
                // 필요에 따라 클라이언트에 에러 메시지를 전송할 수 있습니다.
            }
        }
    }
}

//GPT 응답 브로드캐스트
//                ChatGPTResponse gptResponse = chatService.getGptResponse(message.getMessage());
//                String gptMessageContent = gptResponse.getChoices().get(0).getGptMessage().getContent();
//
//                ChatEntity gptChat = chatService.createChat(teamId, "GPT-3.5", gptMessageContent);
//                ChatMessage gptChatMessage = ChatMessage.builder()
//                        .teamId(teamId)
//                        .sender(gptChat.getSender())
//                        .message(gptChat.getMessage())
//                        .messageType(ChatMessage.MessageType.TALK)
//                        .build();
//                sendingOperations.convertAndSend("/team/" + teamId, gptChatMessage);