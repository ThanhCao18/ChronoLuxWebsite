package com.hau.service.impl;

import com.hau.Enum.NotificationStatus;
import com.hau.converter.Converter;
import com.hau.dto.CommentDTO;
import com.hau.dto.NotificationDTO;
import com.hau.dto.UserDTO;
import com.hau.entity.CommentEntity;
import com.hau.entity.Notification;
import com.hau.repository.CommentRepository;
import com.hau.repository.NotificationRepository;
import com.hau.repository.UserRepository;
import com.hau.service.NotificationService;
import com.hau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private Converter<NotificationDTO, Notification> notificationConverter;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public List<NotificationDTO> getNotificationsForAdmin(Long id) {
        List<Notification> notificationList = notificationRepository.findByAdmins_Id(id);
         return notificationList.stream()
                    .map(notification -> notificationConverter.convertToDTO(notification)).toList();
    }

    @Override
    public void sendNotificationToAdmin(CommentDTO commentDTO, Authentication authentication) {
        Notification notification = new Notification();
        UserDTO userDTO = userService.getCurrentLoggedInCustomer(authentication);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setTitle(userDTO.getFullName() + " đã thêm đánh giá cho sản phẩm " + commentDTO.getProductName());
        notification.setAdmins(userRepository.findAllAdminAccounts());
        Optional<CommentEntity> commentEntity = commentRepository.findById(commentDTO.getId());
        commentEntity.ifPresent(notification::setCommentEntity);
        notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSend("/topic/adminNotifications", notificationConverter.convertToDTO(notification));
    }

    @Override
    public void markAllRead(Authentication authentication) {
        UserDTO userDTO = userService.getCurrentLoggedInCustomer(authentication);
        List<Notification> notifications = notificationRepository.findByAdmins_Id(userDTO.getId());
        List<Notification> unreadNotifications = notifications.stream()
            .filter(noti -> noti.getStatus() == NotificationStatus.UNREAD)
            .toList();
        if(!unreadNotifications.isEmpty()) {
            unreadNotifications.stream().peek(notification -> notification.setStatus(NotificationStatus.READ))
                .forEach(notificationRepository::save);
        }
    }
}
