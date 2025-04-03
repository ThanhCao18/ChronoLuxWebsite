package com.haui.repository;

import com.haui.entity.VoucherConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherConfigRepository extends JpaRepository<VoucherConfig,Long> {
    boolean existsByPrefix(String prefix);
}
