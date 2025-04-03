package com.haui.repository;

import com.haui.entity.CorporationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporationRepository extends JpaRepository<CorporationEntity, Long> {
}
