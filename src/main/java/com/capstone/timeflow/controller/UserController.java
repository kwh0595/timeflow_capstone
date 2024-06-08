package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.UserDTO;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    //마이페이지에서 이름 수정
    @PutMapping("/api/user")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserDTO userDTO, HttpSession session) {
        if (userDTO.getUserName() == null) {
            return ResponseEntity.badRequest().body(null); // 또는 적절한 오류 메시지와 함께 다른 처리를 할 수 있습니다.
        }
        UserEntity updatedUser = userService.updateUser((String) session.getAttribute("userName"),userDTO);
        session.setAttribute("userName", updatedUser.getUserName());
        System.out.println(session.getAttribute("userName"));
        return ResponseEntity.ok(updatedUser);
    }
}
