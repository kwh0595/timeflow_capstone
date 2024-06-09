package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.PasswordDTO;
import com.capstone.timeflow.service.UserService;
import com.capstone.timeflow.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MypagePWController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/api/check-password")
    public ResponseEntity<Map<String, Boolean>> checkPassword(@RequestBody PasswordDTO password, HttpSession session) {
        boolean isValid = userService.isPasswordCorrect((String) session.getAttribute("userName"), password.getPassword());

        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", isValid);

        return new ResponseEntity<>(response, isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }
    @PutMapping("/api/update-password")
    public ResponseEntity<Map<String, Boolean>> updatePassword(@RequestBody PasswordDTO password, HttpSession session) {
        String username = (String) session.getAttribute("userName");
        boolean isUpdated = userService.updatePassword(username, password.getPassword());

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isUpdated);

        if (isUpdated) {
            session.invalidate(); // 비밀번호 변경이 성공하면 세션 무효화
        }

        return new ResponseEntity<>(response, isUpdated ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
