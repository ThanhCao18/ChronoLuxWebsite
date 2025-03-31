package com.hau.repository;

import com.hau.entity.ProductLineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductLineRepository extends JpaRepository<ProductLineEntity,Long> {
    List<ProductLineEntity> findAllByBrand_IdAndActive(Long brandId,boolean active);
    Page<ProductLineEntity> findAllByBrand_IdAndActive(Long brandId, boolean active, Pageable page);
    Page<ProductLineEntity> findAllByActive(boolean active, Pageable page);
    ProductLineEntity findByIdAndActive(long id,boolean active);
    List<ProductLineEntity> findAllByActive(boolean active);
    @Query("SELECT p FROM ProductLineEntity p LEFT JOIN FETCH p.warranty WHERE p.id = :id")
    ProductLineEntity findByIdWithWarranty(@Param("id") Long id);

    Page<ProductLineEntity> findByWarrantyIsNotNull(Pageable pageable);
    Page<ProductLineEntity> findByWarrantyIsNull(Pageable pageable);
    Page<ProductLineEntity> findByBrand_IdAndWarrantyIsNotNull(Long brandId, Pageable pageable);
    Page<ProductLineEntity> findByBrand_IdAndWarrantyIsNull(Long brandId, Pageable pageable);

    @Query("SELECT p FROM ProductLineEntity p WHERE p.name LIKE %?1% AND p.active = ?2")
    Page<ProductLineEntity> findAllByNameContainingIgnoreCaseAndActive(String keyword, boolean active, Pageable pageable);

}
