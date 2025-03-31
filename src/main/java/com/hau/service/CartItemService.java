package com.hau.service;

import com.hau.dto.CartItemDTO;
import com.hau.dto.ProductDTO;
import com.hau.dto.UserDTO;
import com.hau.entity.CartItemEntity;
import com.hau.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CartItemService {
    List<CartItemDTO> findAllByUser(UserDTO userDTO);
    Page<CartItemDTO> findHistoryByUser(Long id, int page, int limit);
    CartItemDTO save(UserDTO userDTO, Long productId,Integer quantity);
    long getTotalPrice(UserDTO userDTO);
    void updateQuantity(UserDTO userDTO,Long productId,Integer quantity);
    void delete(UserDTO userDTO,Long productId);
    Map<String, Integer> findTotalQuantityPerProduct();
    boolean isBuy(UserDTO userDTO,Long productId);
    Integer countSoldProducts(Long productId);
}

