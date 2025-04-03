package com.haui.service;

import com.haui.dto.CommentDTO;
import com.haui.dto.NotificationDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getNotificationsForAdmin(Long id);
    void sendNotificationToAdmin(CommentDTO commentDTO, Authentication authentication);
    void markAllRead(Authentication authentication);
}
