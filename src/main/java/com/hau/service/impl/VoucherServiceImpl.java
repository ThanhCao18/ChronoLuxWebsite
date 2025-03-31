package com.hau.service.impl;

import com.hau.Enum.VoucherType;
import com.hau.converter.Converter;
import com.hau.converter.VoucherConverter;
import com.hau.dto.VoucherConfigDTO;
import com.hau.dto.VoucherDTO;
import com.hau.entity.VoucherConfig;
import com.hau.entity.VoucherEntity;
import com.hau.repository.VoucherConfigRepository;
import com.hau.repository.VoucherRepository;
import com.hau.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherConverter voucherConverter;
    @Autowired
    private VoucherConfigRepository voucherConfigRepository;
    @Autowired
    private Converter<VoucherConfigDTO, VoucherConfig> voucherConfigConverter;
    @Override
    public VoucherDTO findOneByCode(String code) {
        VoucherDTO voucherDTO = null;
        if (code != null && !code.isEmpty()) {
            Date now = new Date();
            if (voucherRepository.findOneByCodeAndValidVoucher(code, now) != null) {
                return voucherConverter.convertToDTO(voucherRepository.findOneByCodeAndValidVoucher(code,now));
            }
        }
        return null;
    }


    @Override
    public Page<VoucherDTO> findValidVouchers(int validVoucherPage, int limit) {
        Pageable pageable = new PageRequest(validVoucherPage - 1, limit);
        Date now = new Date();
        Page<VoucherEntity> voucherEntities = voucherRepository.findValidVouchers(pageable, now);
        return voucherEntities.map(voucherConverter::convertToDTO);
    }

    @Override
    public Page<VoucherDTO> findExpiredVouchers(int expiredVoucherPage, int limit) {
        Pageable pageable = new PageRequest(expiredVoucherPage - 1, limit);
        Date now = new Date();
        Page<VoucherEntity> voucherEntities = voucherRepository.findExpiredVouchers(pageable, now);
        return voucherEntities.map(voucherConverter::convertToDTO);
    }

    @Override
    public void save(VoucherDTO voucherDTO) {
        VoucherEntity voucherEntity = voucherConverter.convertToEntity(voucherDTO);
        voucherEntity.setActive(true);
        voucherEntity.setType(VoucherType.PUBLIC);
        voucherRepository.save(voucherEntity);
    }

    @Override
    public VoucherDTO findOneById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(validId -> voucherRepository.findOneByIdAndActive(validId, true)
                        .filter(v -> !convertToLocalDateTime(v.getEndDay()).isBefore(LocalDateTime.now()))
                        .map(voucherConverter::convertToDTO))
                .orElse(null);
    }

    @Override
    public List<VoucherDTO> findByType(VoucherType voucherType) {
        return voucherRepository.findByTypeAndActive(voucherType,true).stream()
                .filter(voucherEntity ->
                        !convertToLocalDateTime(voucherEntity.getEndDay()).isBefore(LocalDateTime.now()))
                .map(voucherEntity ->
                         voucherConverter.convertToDTO(voucherEntity))
                .toList();
    }

    @Override
    public void setExpiredDate(String code, LocalDateTime date) {
        VoucherEntity voucherEntity = voucherRepository.findOneByCodeAndActive(code,true);
        voucherEntity.setEndDay(convertToDate(date));
        voucherRepository.save(voucherEntity);
    }

    @Override
    @Transactional
    public void deleteVoucher(Long id) {
        VoucherEntity voucherEntity = voucherRepository.findOneByIdAndActive(id, true)
                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));
        voucherEntity.setActive(false);
        voucherRepository.save(voucherEntity);
    }

    @Override
    public VoucherConfigDTO getVoucherConfig() {
        VoucherConfig voucherConfig = voucherConfigRepository.findAll().getFirst();
        if (voucherConfig != null) {
            return voucherConfigConverter.convertToDTO(voucherConfig);
        }
        return null;
    }

    @Override
    public void updateVoucherConfig(VoucherConfigDTO voucherConfigDTO) {
        VoucherConfig voucherConfig = voucherConfigConverter.convertToEntity(voucherConfigDTO);
        voucherConfigRepository.save(voucherConfig);
    }

    private static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
