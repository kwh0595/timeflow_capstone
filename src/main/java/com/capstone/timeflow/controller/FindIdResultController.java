package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.ErrorResponse;
import com.capstone.timeflow.dto.UserRequest;
import com.capstone.timeflow.dto.UserResponse;
import com.capstone.timeflow.service.FindEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/user")
public class FindIdResultController {

    @Autowired
    private FindEmailService findEmailService;

    public FindIdResultController(FindEmailService findEmailService) {
        this.findEmailService = findEmailService;
    }

    @PostMapping("/findIdResult")
    public ResponseEntity<Object> findId(@RequestParam("userName") String userName,
                                         @RequestParam("birthday_year") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate userBirth) {
        try {
            String result = findEmailService.findEmailByNameAndBirthDate(userName, userBirth);
            System.out.println("result: " + result);

            UserResponse response = new UserResponse();
            response.setUserEmail(result);
            System.out.println("response: " + response.getUserEmail());

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            System.out.println("error : CANT FIND : " + e.getMessage());
            // 사용자를 찾을 수 없는 경우 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("사용자를 찾을 수 없습니다."));
        } catch (NullPointerException e) {
            System.out.println("error : NO SARAM : " + e.getMessage());
            // 사용자를 찾을 수 없는 경우 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("존재하지 않는 사용자입니다."));
        } catch (Exception e) {
            System.out.println("error : OTHER ERROR : " + e.getMessage());
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("가입되지 않은 사용자 입니다."));
        }
    }



}