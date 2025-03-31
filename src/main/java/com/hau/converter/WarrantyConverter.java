package com.hau.converter;

import com.hau.dto.WarrantyDTO;
import com.hau.entity.WarrantyEntity;
import org.springframework.stereotype.Component;

@Component
public class WarrantyConverter implements Converter<WarrantyDTO, WarrantyEntity> {
    @Override
    public WarrantyDTO convertToDTO(WarrantyEntity entity) {
        WarrantyDTO warrantyDTO = new WarrantyDTO();
        warrantyDTO.setProductLineId(entity.getProductLineEntity().getId());
        if(entity.getId() != 0L) {
            warrantyDTO.setId(entity.getId());
        }
        warrantyDTO.setContent(entity.getContent());
        warrantyDTO.setProductLineName(entity.getProductLineEntity().getName());
        return warrantyDTO;
    }

    @Override
    public WarrantyEntity convertToEntity(WarrantyDTO dto) {
        WarrantyEntity warrantyEntity = new WarrantyEntity();
        if(dto.getId() != null) {
            warrantyEntity.setId(dto.getId());
        }
        else{
            warrantyEntity.setId(0L);
        }
        warrantyEntity.setContent(dto.getContent());
        return warrantyEntity;
    }
}
