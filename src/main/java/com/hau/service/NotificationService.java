package com.hau.service;

import com.hau.dto.CommentDTO;
import com.hau.dto.NotificationDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getNotificationsForAdmin(Long id);
    void sendNotificationToAdmin(CommentDTO commentDTO, Authentication authentication);
    void markAllRead(Authentication authentication);
}
