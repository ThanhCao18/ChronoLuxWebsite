package com.hau.converter;

import com.hau.dto.CartItemDTO;
import com.hau.entity.CartItemEntity;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter implements Converter<CartItemDTO, CartItemEntity>{
    @Override
    public CartItemDTO convertToDTO(CartItemEntity entity) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        if(entity.getId() != 0){
            cartItemDTO.setId(entity.getId());
        }
        cartItemDTO.setProductId(entity.getProduct().getId());
        if(entity.getUser() != null){
            cartItemDTO.setUsername(entity.getUser().getUserName());
            cartItemDTO.setCustomerName(entity.getUser().getFullName());
            cartItemDTO.setUserId(entity.getUser().getId());
        }
        if(entity.getBill() != null){
            cartItemDTO.setBillId(entity.getBill().getId());
        }
        cartItemDTO.setCreatedBy(entity.getCreateBy());
        cartItemDTO.setCreatedDate(entity.getCreateDate());
        cartItemDTO.setProductName(entity.getProduct().getName());
        cartItemDTO.setProductPrice(entity.getProduct().getPrice());
        cartItemDTO.setQuantity(entity.getQuantity());
        cartItemDTO.setProductImgUrl(entity.getProduct().getImgUrl());
        cartItemDTO.setProductQuantity(entity.getProduct().getInstock());
        return cartItemDTO;
    }

    @Override
    public CartItemEntity convertToEntity(CartItemDTO cartItemDTO) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setQuantity(Integer.parseInt(cartItemDTO.getQuantity()));
        return cartItemEntity;
    }
}
