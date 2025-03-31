package com.hau.converter;

import com.hau.dto.ProductLineDTO;
import com.hau.entity.ProductLineEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductLineConverter implements Converter< ProductLineDTO,ProductLineEntity>{


    @Override
    public ProductLineDTO convertToDTO(ProductLineEntity entity) {
        ProductLineDTO productLineDTO = new ProductLineDTO();
        productLineDTO.setId(entity.getId());
        productLineDTO.setName(entity.getName());
        productLineDTO.setBrandId(entity.getBrand().getId());
        if(entity.getWarranty() != null){
            productLineDTO.setWarrantyId(entity.getWarranty().getId());
        }
        else{
            productLineDTO.setWarrantyId(0);
        }
        productLineDTO.setIconUrl(entity.getIconUrl());
        productLineDTO.setBannerUrl(entity.getBanner());
        return productLineDTO;
    }

    @Override
    public ProductLineEntity convertToEntity(ProductLineDTO dto) {
        ProductLineEntity productLineEntity = new ProductLineEntity();
        if(dto.getId() != null){
            productLineEntity.setId(dto.getId());
        }
        productLineEntity.setName(dto.getName());
        productLineEntity.setIconUrl(dto.getIconUrl());
        productLineEntity.setBanner(dto.getBannerUrl());
        return productLineEntity;
    }
}
