package com.haui.repository;

import com.haui.entity.CartItemEntity;
import com.haui.entity.ProductEntity;
import com.haui.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {
    List<CartItemEntity> findAllByUser(UserEntity user);
    CartItemEntity findOneByUserAndProduct(UserEntity user, ProductEntity productEntity);
    Page<CartItemEntity> findByUserId(Pageable pageable, Long userId);

    @Query("SELECT c.product AS product, SUM(c.quantity) AS quantityTotal " +
            "FROM CartItemEntity c " +
            "GROUP BY c.product " +
            "ORDER BY quantityTotal DESC")
    List<Object[]> findTotalQuantityPerProduct();

    List<CartItemEntity> findByUserId(Long userId);
    Integer countByProductId(Long productId);

}
