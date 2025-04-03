package com.haui.converter;

import com.haui.dto.BillDTO;
import com.haui.entity.BillEntity;
import org.springframework.stereotype.Component;

@Component
public class BillConverter implements Converter<BillDTO, BillEntity> {
    @Override
    public BillDTO convertToDTO(BillEntity entity) {
        BillDTO billDTO = new BillDTO();
        billDTO.setId(entity.getId());
        billDTO.setCreatedDate(entity.getCreateDate());
        billDTO.setModifiedDate(entity.getModifiedDate());
        billDTO.setModifiedBy(entity.getModifiedBy());
        billDTO.setGender(entity.getGender());
        billDTO.setStreet(entity.getAddress().split("-")[0]);
        billDTO.setWard(entity.getAddress().split("-")[1]);
        billDTO.setDistrict(entity.getAddress().split("-")[2]);
        billDTO.setCity(entity.getAddress().split("-")[3]);
        billDTO.setUsername(entity.getDisplayName());
        billDTO.setPhone(entity.getPhone());
        billDTO.setNote(entity.getNote());
        billDTO.setTotal(entity.getTotal());
        billDTO.setDisplayName(entity.getDisplayName());
        billDTO.setStatus(entity.getStatus());
        billDTO.setEmail(entity.getEmail());
        billDTO.setSubtotal(entity.getSubtotal());
        billDTO.setReceiverName(entity.getReceiverName());
        billDTO.setReceiverGender(entity.getReceiverGender());
        billDTO.setReceiverPhone(entity.getReceiverPhone());
        billDTO.setCancelReason(entity.getCancelReason());

        billDTO.setDeliveryStatus (entity.getDeliveryStatus());
        if(entity.getPaymentMethod().equals("cod")) {
            billDTO.setPaymentMethod("Thanh toán khi nhận hàng");
        }
        else if(entity.getPaymentMethod().equals("bank-transfer")) {
            billDTO.setPaymentMethod("Chuyển khoản");
        }
        else{
            billDTO.setPaymentMethod(entity.getPaymentMethod());
        }
        if(entity.getVoucher() != null){
            billDTO.setVoucherCode(entity.getVoucher().getCode());
            billDTO.setDiscount(entity.getVoucher().getDiscount());
        }
        else{
            billDTO.setVoucherCode("Không có");
            billDTO.setDiscount(0);
        }
        return billDTO;
    }

    @Override
    public BillEntity convertToEntity(BillDTO dto) {
        BillEntity billEntity = new BillEntity();
        billEntity.setGender(dto.getGender());
        billEntity.setAddress(dto.getStreet() +"-"+dto.getWard()+"-"+dto.getDistrict()+"-"+dto.getCity());
        billEntity.setDisplayName(dto.getUsername());
        billEntity.setPhone(dto.getPhone());
        billEntity.setNote(dto.getNote());
        billEntity.setTotal(dto.getTotal());
        billEntity.setDisplayName(dto.getDisplayName());
        billEntity.setCancelReason(dto.getCancelReason());
        billEntity.setStatus(dto.getStatus());
        billEntity.setEmail(dto.getEmail());
        billEntity.setSubtotal(dto.getSubtotal());
        billEntity.setReceiverName(dto.getReceiverName());
        billEntity.setReceiverGender(dto.getReceiverGender());
        billEntity.setReceiverPhone(dto.getReceiverPhone());
        billEntity.setPaymentMethod(dto.getPaymentMethod());
        billEntity.setDeliveryStatus(dto.getDeliveryStatus());
        return billEntity;
    }
}
