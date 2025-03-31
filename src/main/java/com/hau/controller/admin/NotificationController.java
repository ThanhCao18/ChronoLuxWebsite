package com.hau.controller.admin;

import com.hau.dto.NotificationDTO;
import com.hau.dto.UserDTO;
import com.hau.service.NotificationService;
import com.hau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications(@AuthenticationPrincipal Authentication authentication) {
        UserDTO userDTO = userService.getCurrentLoggedInCustomer(authentication);
        List<NotificationDTO> notifications = notificationService.getNotificationsForAdmin(userDTO.getId());
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/mark-notifications-read")
    public ResponseEntity<?> markNotificationsRead(@AuthenticationPrincipal Authentication authentication) {
        notificationService.markAllRead(authentication);
        return ResponseEntity.ok().build();
    }

}
