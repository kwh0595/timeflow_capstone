package com.capstone.timeflow.controller;

import com.capstone.timeflow.dto.ErrorResponse;
import com.capstone.timeflow.dto.UserResponse;
import com.capstone.timeflow.service.FindEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "USER_1", description = "사용자 아이디 찾기 관련")
@Controller
@RequestMapping("/user")
public class FindIdController {

    @Autowired
    private FindEmailService findEmailService;

    public FindIdController(FindEmailService findEmailService) {
        this.findEmailService = findEmailService;
    }

    // 아이디 찾기 페이지 요청
    @Operation(summary = "아이디 찾기", description = "아이디찾기 페이지 이동")
    @GetMapping("/findId")
    public String findIdForm() {
        return "findId";
    }

    @Operation(summary = "아이디 찾기", description = "아이디찾기 매서드 실행")
    @PostMapping("/findIdResult")
    public ResponseEntity<Object> findId(@RequestParam("userName") String userName,
                                         @  RequestParam("birthday_year") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate userBirth) {
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