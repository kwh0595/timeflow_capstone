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
public class FindIdResultController {

    @Autowired
    private FindEmailService findEmailService;

/*    @GetMapping("/user/findIdResult")
    public String findIdResult(@RequestParam String userEmail, @RequestParam boolean isSuccess, Model model) {

        model.addAttribute("userEmail", userEmail);
        model.addAttribute("isSuccess", isSuccess);
        System.out.println("model = " + model);
        return "findIdResult";
    }*/


    @PostMapping("/user/findIdResult")
    public String findIdResult2(@RequestParam("userName") String userName,
                               @RequestParam("birthday_year") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate userBirth,
                               Model model) {

        try {
            String userEmail = findEmailService.findEmailByNameAndBirthDate(userName, userBirth);
            System.out.println("userEmail: "+userEmail);
            model.addAttribute("userEmail", userEmail);
            model.addAttribute("isSucce ss", true);
        } catch (IllegalArgumentException e) {
            model.addAttribute("isSuccess", false);
        }
        return "findIdResult";
    }

    @GetMapping("/user/findIdResult")
    public String showFindIdResultPage() {
        return "findIdResult"; // "findIdResult"는 GET 요청 시 결과를 보여줄 view 이름입니다.
    }

}