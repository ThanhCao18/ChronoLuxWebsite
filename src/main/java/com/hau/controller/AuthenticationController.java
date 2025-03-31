package com.hau.controller;


import com.hau.dto.UserDTO;
import com.hau.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkAuthentication(@AuthenticationPrincipal Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("authenticated", false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.put("authenticated", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/userId")
    public ResponseEntity<?> getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                Map<String, Object> response = new HashMap<>();
                response.put("error", "Không có phiên đăng nhập hợp lệ");
                response.put("authenticated", false);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            UserDTO userDTO = userService.getCurrentLoggedInCustomer(authentication);

            if (userDTO == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("error", "Không tìm thấy thông tin người dùng");
                response.put("authenticated", true);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            return ResponseEntity.ok(userDTO.getId());

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Lỗi server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/rasa/check-auth")
    @ResponseBody
    public Map<String, Object> checkAuthentication(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Lấy session
        HttpSession session = request.getSession(false);
        response.put("sessionExists", session != null);
        response.put("sessionId", session != null ? session.getId() : "không có session");

        // Lấy cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Map<String, String> cookieMap = new HashMap<>();
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
            response.put("cookies", cookieMap);
        } else {
            response.put("cookies", "không có cookies");
        }

        // Kiểm tra authentication từ SecurityContextHolder
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        response.put("hasAuthentication", auth != null);

        boolean isAuthenticated = (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken));
        response.put("authenticated", isAuthenticated);

        return response;
    }

}
