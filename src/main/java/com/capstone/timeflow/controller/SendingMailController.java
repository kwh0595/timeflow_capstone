/*
package com.capstone.timeflow.controller;

import com.capstone.timeflow.service.RandomPasswordService;
import com.capstone.timeflow.service.SendingMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@Controller
public class SendingMailController {
    @Autowired
    private SendingMailService sendingMailService;

    @Autowired
    private RandomPasswordService randomPasswordService;

    @RequestMapping("/noticeMail")
    public ModelAndView sendEMail(@RequestParam(required = false) String id, @RequestParam String email) throws Exception{
        ModelAndView mv = new ModelAndView();

        String randomPassword = randomPasswordService.generateRandomPassword();

        String addr = "kimwalwa0619@gmail.com";
        String subject = "TimeFlow 비밀번호 재설정";
        String body = "랜덤하게 재생성된 비밀번호를 부여해드립니다. 해당 비밀번호로 로그인 후 빠른 시일 내에 재설정하시는 것을 권장드립니다.\n\n"
                +"새 비밀번호 : " + randomPassword + "\n\n"
                + "로그인 후, 개인 정보 설정 페이지를 통해 비밀번호를 변경해주세요.";
        sendingMailService.sendEmail(email, addr, subject, body);

        mv.setViewName("/");
        return mv;
    }
}
*/