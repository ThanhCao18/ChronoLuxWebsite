package com.hau.converter;

import com.hau.dto.PostDTO;
import com.hau.entity.BaseEntity;
import com.hau.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostConverter implements Converter<PostDTO, PostEntity> {
    @Override
    public PostDTO convertToDTO(PostEntity entity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(entity.getId());
        postDTO.setImg(entity.getImg());
        postDTO.setCaption(entity.getCaption());
        postDTO.setContent(entity.getContent());
        return postDTO;
    }

    @Override
    public PostEntity convertToEntity(PostDTO dto) {
        PostEntity postEntity = new PostEntity();
        if(dto.getId() != null) {
            postEntity.setId(dto.getId());
        }
        postEntity.setImg(dto.getImg());
        postEntity.setCaption(dto.getCaption());
        postEntity.setContent(dto.getContent());
        return postEntity;
    }
}
