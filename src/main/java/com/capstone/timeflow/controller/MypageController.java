package com.capstone.timeflow.controller;
import com.capstone.timeflow.dto.UserDTO;
import com.capstone.timeflow.entity.UserEntity;
import com.capstone.timeflow.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class MypageController {
    private final UserService userService;

    @GetMapping("/api/user")
    public Map<String, String> getSession(HttpSession session ) {
        // 세션에서 사용자 정보 가져오기
        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        String userMail = (String) session.getAttribute("userMail");

        // 반환할 응답 객체 생성
        Map<String, String> response = new HashMap<>();

        // 사용자 ID, 이름, 메일을 검사하여 응답에 추가
        if (userId == null) {
            response.put("error", "No userId set in session");
        } else {
            // 사용자 ID는 String 타입으로 변환하여 추가
            response.put("userId", String.valueOf(userId));
            // 사용자 이름과 메일 추가
            response.put("userName", userName != null ? userName : "No userName set in session");
            response.put("userMail", userMail != null ? userMail : "No userMail set in session");
        }

        // 디버그 목적으로 콘솔에 로그 출력
        System.out.println("UserID: " + userId + ", UserName: " + userName + ", UserMail: " + userMail);

        return response;
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