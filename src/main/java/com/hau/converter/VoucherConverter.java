package com.hau.converter;

import com.hau.dto.VoucherDTO;
import com.hau.entity.VoucherEntity;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class VoucherConverter implements Converter<VoucherDTO, VoucherEntity>{
    @Override
    public VoucherDTO convertToDTO(VoucherEntity entity) {
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setId(entity.getId());
        voucherDTO.setCode(entity.getCode());
        voucherDTO.setDiscount(entity.getDiscount());
        voucherDTO.setBeginDay(entity.getBeginDay().toString());
        voucherDTO.setEndDay(entity.getEndDay().toString());
        voucherDTO.setCreatedDate(entity.getCreateDate());
        voucherDTO.setCreatedBy(entity.getCreateBy());
        voucherDTO.setVoucherType(entity.getType());
        return voucherDTO;
    }

    @Override
    public VoucherEntity convertToEntity(VoucherDTO dto) {
        VoucherEntity voucherEntity = new VoucherEntity();
        voucherEntity.setCode(dto.getCode());
        voucherEntity.setDiscount(dto.getDiscount());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(dto.getBeginDay(), formatter);
        LocalDate endDate = LocalDate.parse(dto.getEndDay(), formatter);

        LocalTime cur = LocalTime.now();
        LocalDateTime beginDateTime = beginDate.atTime(cur);
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        voucherEntity.setBeginDay(Timestamp.valueOf(beginDateTime));
        voucherEntity.setEndDay(Timestamp.valueOf(endDateTime));
        voucherEntity.setType(dto.getVoucherType());
        return voucherEntity;
    }
}
