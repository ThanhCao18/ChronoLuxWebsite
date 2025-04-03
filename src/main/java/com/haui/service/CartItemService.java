package com.haui.service;

import com.haui.dto.CartItemDTO;
import com.haui.dto.UserDTO;
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

