package com.haui.converter;

import com.haui.Enum.NotificationStatus;
import com.haui.dto.NotificationDTO;
import com.haui.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter implements Converter<NotificationDTO, Notification> {

    @Override
    public NotificationDTO convertToDTO(Notification entity) {

        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setCreatedDate(entity.getCreateDate());

        notificationDTO.setProductId(entity.getCommentEntity().getProduct().getId());

        notificationDTO.setUsername(entity.getCommentEntity().getUser().getFullName());

        notificationDTO.setAvatarUrl(entity.getCommentEntity().getUser().getImgUrl());

        notificationDTO.setProductName(entity.getCommentEntity().getProduct().getName());

        notificationDTO.setTitle(entity.getTitle());

        if(entity.getStatus().equals(NotificationStatus.READ)){
            notificationDTO.setStatus("Read");
        }
        else {
            notificationDTO.setStatus("Unread");
        }

        return notificationDTO;
    }

    @Override
    public Notification convertToEntity(NotificationDTO dto) {
        return null;
    }
}
