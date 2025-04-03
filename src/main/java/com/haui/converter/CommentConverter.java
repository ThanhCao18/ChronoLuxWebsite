package com.haui.converter;

import com.haui.dto.CommentDTO;
import com.haui.entity.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Converter<CommentDTO, CommentEntity>{
    @Override
    public CommentDTO convertToDTO(CommentEntity entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(entity.getId());
        commentDTO.setProductId(entity.getProduct().getId());
        commentDTO.setProductName(entity.getProduct().getName());
        if(entity.getUser() != null){
            commentDTO.setUserId(entity.getUser().getId());
            commentDTO.setImgUrl(entity.getUser().getImgUrl());
        }
        commentDTO.setName(entity.getName());
        commentDTO.setRating(entity.getRating());
        commentDTO.setReview(entity.getReview());
        commentDTO.setImgReviewUrl(entity.getImgReviewUrl());
        commentDTO.setCreatedDate(entity.getCreateDate());
        commentDTO.setLikeCount(entity.getLikeCount());
        return  commentDTO;
    }

    @Override
    public CommentEntity convertToEntity(CommentDTO dto) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setRating(dto.getRating());
        commentEntity.setReview(dto.getReview());
        commentEntity.setImgReviewUrl(dto.getImgReviewUrl());
        commentEntity.setName(dto.getName());
        commentEntity.setLikeCount(dto.getLikeCount());
        return commentEntity;
    }
}
