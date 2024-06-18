package com.capstone.timeflow.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    @GetMapping("")
    public String chatpage() {
        return "chat";
    }
}