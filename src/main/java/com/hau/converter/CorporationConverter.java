package com.hau.converter;

import com.hau.dto.CorporationDTO;
import com.hau.entity.CorporationEntity;
import org.springframework.stereotype.Component;

@Component
public class CorporationConverter implements Converter<CorporationDTO, CorporationEntity>{
    @Override
    public CorporationDTO convertToDTO(CorporationEntity entity) {
        CorporationDTO dto = new CorporationDTO();
        dto.setId(entity.getId());
        dto.setAbout(entity.getAbout());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setImg(entity.getImgUrl());
        return dto;
    }

    @Override
    public CorporationEntity convertToEntity(CorporationDTO dto) {
        CorporationEntity entity = new CorporationEntity();
        if(dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setAbout(dto.getAbout());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setImgUrl(dto.getImg());
        return entity;
    }
}
