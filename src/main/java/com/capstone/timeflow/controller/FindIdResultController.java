package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.FindEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Controller
public class FindIdResultController {

    @Autowired
    private FindEmailService findEmailService;
    @PostMapping("/user/findId")
    public String findIdResult(@RequestParam("userName") String userName,
                               @RequestParam("birthday_year") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate userBirth,
                               Model model) {

        try {
            String userEmail = findEmailService.findEmailByNameAndBirthDate(userName, userBirth);
            model.addAttribute("userEmail", userEmail); // 사용자 이메일 추가
            model.addAttribute("isSuccess", true); // 성공 상태 추가
            System.out.println(userEmail);
            return "findIdResult"; // 사용자 이메일을 출력할 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("isSuccess", false); // 실패 상태 추가
            return "findIdResult"; // 오류 메시지를 출력할 페이지
        }
    }
}
