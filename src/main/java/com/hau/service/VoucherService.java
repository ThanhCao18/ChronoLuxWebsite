package com.hau.service;

import com.hau.Enum.VoucherType;
import com.hau.dto.VoucherConfigDTO;
import com.hau.dto.VoucherDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface VoucherService {
    VoucherDTO findOneByCode(String code);
    Page<VoucherDTO> findValidVouchers(int validVoucherPage, int limit);
    Page<VoucherDTO> findExpiredVouchers(int expiredVoucherPage, int limit);
    void save(VoucherDTO voucherDTO);
    VoucherDTO findOneById(Long id);
    List<VoucherDTO> findByType(VoucherType voucherType);
    void setExpiredDate(String code, LocalDateTime date);
    void deleteVoucher(Long id);
    VoucherConfigDTO getVoucherConfig();
    void updateVoucherConfig(VoucherConfigDTO voucherConfigDTO);
}
