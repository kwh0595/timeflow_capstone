package com.capstone.timeflow.controller;

import com.capstone.timeflow.entity.CustomUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    @GetMapping("/getSessionUsername")
    public Map<String, String> getSessionUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        String userName = customUser.getUserName();

        Map<String, String> response = new HashMap<>();
        if (userName == null) {
            response.put("userName", "No username set in session");
        } else {
            response.put("userName", userName);
        }
        return response;
    }
}

