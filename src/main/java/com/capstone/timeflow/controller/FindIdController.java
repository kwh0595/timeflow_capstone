package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.FindEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FindIdController {

    @Autowired
    private FindEmailService findEmailService;

    // 아이디 찾기 페이지 요청
    @GetMapping("/user/findId")
    public String findIdForm() {
        return "findId";
    }
}