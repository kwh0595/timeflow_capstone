package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.LoginRequest;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserServiceImpl userService;

    @GetMapping("/")
    public String loginPage(Model model) {

        model.addAttribute("LoginRequest", new LoginRequest());
        return "login";
    }
    @PostMapping("/main")
    public String login(@Validated @ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest, Model model) {

        UserEntity userEntity = userService.login(loginRequest);

        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(userEntity == null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "login";
        }

        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
        // 세션에 userId를 넣어줌
        session.setAttribute("userId", userEntity.getUserId());
        session.setAttribute("userName", userEntity.getUserName());

        session.setMaxInactiveInterval(10800); // Session이 3시간동안 유지
        System.out.println("Saved userId in session: " + session.getAttribute("userId"));
        System.out.println("login success");
        return "main";
    }
    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

}