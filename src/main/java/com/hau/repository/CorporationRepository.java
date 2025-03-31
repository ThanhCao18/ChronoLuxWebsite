package com.hau.repository;

import com.hau.entity.CorporationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporationRepository extends JpaRepository<CorporationEntity, Long> {
}
