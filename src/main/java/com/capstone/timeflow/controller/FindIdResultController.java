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


/*    @GetMapping("/findIdResult")
    public String findIdResult() {
        System.out.println("GET /FINDIDRESULT");
        return "findIdResult";
    }*/
/*
    @PostMapping
    public String findIdResult(@RequestParam("userName") String userName,
                               @RequestParam("birthday_year") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate userBirth,
                               RedirectAttributes redirectAttributes) {

        String userEmail = findEmailService.findEmailByNameAndBirthDate(userName, userBirth);
        try {
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
            //model.addAttribute("isSuccess", false);DispatcherServlet        : POST "/user/findIdResult", parameters={userName:[김여정], birthday_year:[2002-06-19]}
            //2024-06-05T00:00:02.653+09:00 DEBUG 9656 --- [io-8082-exec-10] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.capstone.timeflow.controller.FindIdResultController#findId(String, LocalDate)
            //Hibernate: select ue1_0.user_id,ue1_0.role,ue1_0.team_entity_team_id,ue1_0.user_age,ue1_0.user_birth,ue1_0.user_join_date,ue1_0.user_mail,ue1_0.user_name,ue1_0.user_password,ue1_0.user_sex from user ue1_0 where ue1_0.user_name=? and ue1_0.user_birth=?
            //result: ehxhfl643@naver.com
            //response: com.capstone.timeflow.dto.UserResponse@7a681de0
        }
        return "redirect:/user/findIdResult";
    }

    @GetMapping
    public String showFindIdResultPage(Model model, @RequestParam(required = false) String userName,
                                       @RequestParam(required = false) LocalDate userBirth,
                                       @RequestParam(required = false) String userEmail) {
        if (userEmail == null) {
            System.out.println("no ~~ model");
            model.addAttribute("userName", userName);
            model.addAttribute("userBirth", userBirth);
            model.addAttribute("userEmail", "정보가 없습니다."); // 기본 메시지 설정
        } else {
            System.out.println(userName + userBirth + userEmail);
            model.addAttribute("userName", userName);
            model.addAttribute("userBirth", userBirth);
            model.addAttribute("userEmail", userEmail);
        }
        return "findIdResult"; // "findIdResult"는 GET 요청 시 결과를 보여줄 view 이름입니다.
    }
*/
}