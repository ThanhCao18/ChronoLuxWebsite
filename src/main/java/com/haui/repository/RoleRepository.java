package com.haui.repository;

import com.haui.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    public RoleEntity findOneByCode(String code);
}
