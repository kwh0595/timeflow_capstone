package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.FindPWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindPWResultController {

    @Autowired
    private FindPWService findPWService;

    @PostMapping("/user/findPassword")
    public String findPassword(@RequestParam String userEmail) {
        findPWService.resetPasswordAndSendEmail(userEmail);
        System.out.println("성공");
        return "findPassword";
    }
}