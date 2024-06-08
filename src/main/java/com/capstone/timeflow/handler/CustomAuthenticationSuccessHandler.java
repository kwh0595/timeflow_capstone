package com.capstone.timeflow.handler;

import com.capstone.timeflow.entity.CustomUser;
import com.capstone.timeflow.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        HttpSession session = request.getSession();
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        UserEntity userEntity = userDetails.getUserEntity();
        session.setAttribute("userMail", userEntity.getUserMail());
        session.setAttribute("userId", userEntity.getUserId());
        session.setAttribute("userName", userEntity.getUserName());
        System.out.println("AuthenticationSuccessHandler 호출됨");
        System.out.println("userMail: " + userEntity.getUserMail());

        // 리다이렉션 처리
        response.sendRedirect("/main");
    }
}