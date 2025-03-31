package com.hau.repository;

import com.hau.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    public RoleEntity findOneByCode(String code);
}
