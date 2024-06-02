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

    @GetMapping("/user/findIdResult")
    public String showFindIdResult(Model model) {
        return "findIdResult";
    }
    @PostMapping("/user/findId")
    public String findIdResult(@RequestParam("userName") String userName,
                               @RequestParam("birthday_year") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate userBirth,
                               RedirectAttributes redirectAttributes) {

        try {
            String userEmail = findEmailService.findEmailByNameAndBirthDate(userName, userBirth);
            System.out.println("userEmail: "+userEmail);
            redirectAttributes.addAttribute("userEmail", userEmail);
            redirectAttributes.addAttribute("isSuccess", true);
            return "redirect:/user/findIdResult";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addAttribute("isSuccess", false);
            return "redirect:/user/findIdResult";
        }
    }
}