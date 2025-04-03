package com.haui.converter;

import com.haui.dto.VoucherConfigDTO;
import com.haui.entity.VoucherConfig;
import org.springframework.stereotype.Component;

@Component
public class VoucherConfigConverter implements Converter<VoucherConfigDTO, VoucherConfig> {
    @Override
    public VoucherConfigDTO convertToDTO(VoucherConfig entity) {
        VoucherConfigDTO dto = new VoucherConfigDTO();
        dto.setId(entity.getId());
        dto.setPrefix(entity.getPrefix());
        dto.setDiscountDefault(entity.getDiscountDefault());
        return dto;
    }

    @Override
    public VoucherConfig convertToEntity(VoucherConfigDTO dto) {
        VoucherConfig entity = new VoucherConfig();
        entity.setId(dto.getId());
        entity.setPrefix(dto.getPrefix());
        entity.setDiscountDefault(dto.getDiscountDefault());
        return entity;
    }
}
