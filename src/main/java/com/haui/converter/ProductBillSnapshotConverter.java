package com.haui.converter;

import com.haui.dto.ProductBillSnapshotDTO;
import com.haui.entity.ProductBillSnapshot;
import org.springframework.stereotype.Component;

@Component
public class ProductBillSnapshotConverter implements Converter<ProductBillSnapshotDTO, ProductBillSnapshot> {

    @Override
    public ProductBillSnapshotDTO convertToDTO(ProductBillSnapshot entity) {
        ProductBillSnapshotDTO dto = new ProductBillSnapshotDTO();
        dto.setName(entity.getProductName());
        dto.setImage(entity.getImgUrl());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setTotal(entity.getPrice() * entity.getQuantity());
        return dto;
    }

    @Override
    public ProductBillSnapshot convertToEntity(ProductBillSnapshotDTO dto) {
        return null;
    }
}


