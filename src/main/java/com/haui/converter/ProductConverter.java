package com.haui.converter;

import com.haui.dto.ProductDTO;
import com.haui.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ProductDTO toDTO(ProductEntity productEntity){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setWaterResistant(productEntity.getWaterResistant());
        productDTO.setFaceSize(productEntity.getFaceSize());
        productDTO.setGender(productEntity.getGender());
        productDTO.setGlassMaterial(productEntity.getGlassMaterial());
        productDTO.setStock(productEntity.getInstock());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setThickness(productEntity.getThickness());
        productDTO.setWatchType(productEntity.getWatchType());
        productDTO.setImgUrl(productEntity.getImgUrl());
        productDTO.setStrapMaterial(productEntity.getStrapMaterial());
        productDTO.setName(productEntity.getName());
        productDTO.setProductLineName(productEntity.getProductLine().getName());
        productDTO.setBrandName(productEntity.getProductLine().getBrand().getName());
        productDTO.setCountry(productEntity.getProductLine().getBrand().getCountry());
        productDTO.setBrandId(productEntity.getProductLine().getBrand().getId());
        productDTO.setProductLineId(productEntity.getProductLine().getId());
        productDTO.setDescription(productEntity.getDescription());
        if(productEntity.getProductLine().getWarranty() != null){
            productDTO.setWarrantyContent(productEntity.getProductLine().getWarranty().getContent());
        }
        if(productEntity.getRatings() == null){
            productDTO.setCommentCount(0);
        }
        else{
            productDTO.setCommentCount(productEntity.getRatings().size());
        }
        return productDTO;
    }
    public ProductEntity toEntity(ProductDTO productDTO){
        ProductEntity productEntity = new ProductEntity();
        if(productDTO.getId() != null){
            productEntity.setId(productDTO.getId());
        }
        productEntity.setWaterResistant(productDTO.getWaterResistant());
        productEntity.setFaceSize(productDTO.getFaceSize());
        productEntity.setGender(productDTO.getGender());
        productEntity.setGlassMaterial(productDTO.getGlassMaterial());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setThickness(productDTO.getThickness());
        productEntity.setWatchType(productDTO.getWatchType());
        productEntity.setImgUrl(productDTO.getImgUrl());
        productEntity.setStrapMaterial(productDTO.getStrapMaterial());
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setInstock(productDTO.getStock());
        return productEntity;
    }
}
