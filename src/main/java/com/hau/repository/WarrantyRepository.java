package com.hau.repository;

import com.hau.entity.WarrantyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WarrantyRepository extends JpaRepository<WarrantyEntity, Long> {
    WarrantyEntity findByProductLineEntity_IdAndProductLineEntity_Active(Long id, boolean active);
    @Query("SELECT COUNT(w) > 0 FROM WarrantyEntity w WHERE w.id = :id")
    boolean existsById(@Param("id") Long id);
}
