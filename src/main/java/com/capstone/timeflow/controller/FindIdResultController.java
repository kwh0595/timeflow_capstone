package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.FindEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/user/findIdResult")
public class FindIdResultController {

    @Autowired
    private FindEmailService findEmailService;

    @PostMapping
    public String findIdResult(@RequestParam("userName") String userName,
                               @RequestParam("birthday_year") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate userBirth,
                               RedirectAttributes redirectAttributes) {

        try {
            String userEmail = findEmailService.findEmailByNameAndBirthDate(userName, userBirth);
            System.out.println("userEmail: "+userEmail);
            redirectAttributes.addAttribute("userName", userName);
            redirectAttributes.addAttribute("userBirth", userBirth);
            redirectAttributes.addAttribute("userEmail", userEmail);
            System.out.println(redirectAttributes);
            //model.addAttribute("isSuccess", true);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            redirectAttributes.addAttribute("userName", userName);
            redirectAttributes.addAttribute("userBirth", userBirth);
            redirectAttributes.addAttribute("userEmail", "모델을 받지 못했습니다");
            //model.addAttribute("isSuccess", false);
        }
        return "redirect:/user/findIdResult/result";
    }

    @GetMapping("/result")
    public String showFindIdResultPage(Model model, @RequestParam(required = false) String userName,
                                       @RequestParam(required = false) LocalDate userBirth,
                                       @RequestParam(required = false) String userEmail) {
        if (userEmail == null) {
            System.out.println("no ~~ model");
            model.addAttribute("userName", userName);
            model.addAttribute("userBirth", userBirth);
            model.addAttribute("userEmail", "정보가 없습니다."); // 기본 메시지 설정
        } else {
            System.out.println("good");
            model.addAttribute("userName", userName);
            model.addAttribute("userBirth", userBirth);
            model.addAttribute("userEmail", userEmail);
        }
        return "findIdResult"; // "findIdResult"는 GET 요청 시 결과를 보여줄 view 이름입니다.
    }
}