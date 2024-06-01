package com.capstone.timeflow.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Log4j2
public class ChatController {

    @GetMapping("/team/chat")
    public String chatGET(@SessionAttribute(name = "userName", required = false) String userName, Model model){
        log.info("@ChatController, chat GET()");
        System.out.println(userName);
        return "chating";
    }
    @GetMapping("/chat")
    public String chatpage(){
        return "chat";
    }
}