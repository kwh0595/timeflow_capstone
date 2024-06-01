package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.UserDTO;
import com.capstone.timeflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //회원가입 페이지 출력 요청
    @GetMapping("/user/signup")
    public String signupForm(){
      return "signUp";
    }
    @PostMapping("/user/signup")
    public String signup(@ModelAttribute UserDTO userDTO){
        System.out.println("UserController.signup");
        userService.save(userDTO);
        return "login";
    }
}
