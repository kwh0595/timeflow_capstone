package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserServiceImpl userService;

    @GetMapping("/")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }
}