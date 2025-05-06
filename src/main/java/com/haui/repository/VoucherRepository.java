package com.haui.repository;

import com.haui.Enum.VoucherType;
import com.haui.entity.VoucherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<VoucherEntity,Long> {

    VoucherEntity findOneByCodeAndActive(String code, boolean active);
    @Query("SELECT v FROM VoucherEntity v WHERE v.code = :code AND :currentDate BETWEEN v.beginDay AND v.endDay AND v.active = true")
    VoucherEntity findOneByCodeAndValidVoucher(@Param("code") String code, @Param("currentDate") Date currentDate);

    @Query("SELECT v FROM VoucherEntity v WHERE v.endDay >= ?1 AND v.active = true ORDER BY v.beginDay DESC")
    Page<VoucherEntity> findValidVouchers(Pageable pageable, Date now);

    @Query("SELECT v FROM VoucherEntity v WHERE v.endDay < ?1 AND v.active = true ORDER BY v.beginDay DESC")
    Page<VoucherEntity> findExpiredVouchers(Pageable pageable, Date now);


    List<VoucherEntity> findByTypeAndActive(VoucherType type,boolean active);

    Optional<VoucherEntity>  findOneByIdAndActive(Long id, boolean active);

    boolean existsByCode(String code);
}
