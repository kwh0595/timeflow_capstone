package com.capstone.timeflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {

    @GetMapping("/user/mypage")
    public String mypage() {
        return "mypage";
    }
}
