package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.FindPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FindPWController {

    // 비밀번호 찾기 페이지 요청
    @GetMapping("/user/findPassword")
    public String findPasswordForm() {
        return "findPassword";
    }
}
