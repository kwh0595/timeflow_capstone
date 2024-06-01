package com.capstone.timeflow.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GetSessionController {
    @GetMapping("/setSession")
    public String setSession(HttpSession session, @RequestParam Long userId) {
        session.setAttribute("userId", userId);
        return "Session userID set to " + userId;
    }

    @GetMapping("/getSession")
    public Map<Long, String> getSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Map<Long, String> response = new HashMap<>();
        if (userId == null) {
            response.put(userId, "No userId set in session");
        } else {
            response.put(userId, "userId");
        }

        return response;
    }
}
