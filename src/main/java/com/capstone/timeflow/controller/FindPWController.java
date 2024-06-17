package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.FindPWService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "USER_2", description = "사용자 비밀번호 찾기 관련")
@Controller
@RequestMapping("/user")
public class FindPWController {

    @Autowired
    private FindPWService findPWService;

    // 비밀번호 찾기 페이지 요청
    @Operation(summary = "비밀번호 찾기", description = "비밀번호찾기 페이지 이동")

    @GetMapping("/findPassword")
    public String findPasswordForm() {
        return "findPassword";
    }

    @Operation(summary = "비밀번호 찾기", description = "비밀번호찾기 메서드 실행")
    @PostMapping("/user/findPassword")
    public String findPassword(@RequestParam String userEmail) {
        findPWService.resetPasswordAndSendEmail(userEmail);
        System.out.println("성공");
        return "findPassword";
    }
}
