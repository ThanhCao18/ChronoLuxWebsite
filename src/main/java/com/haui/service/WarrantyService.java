package com.haui.service;

import com.haui.dto.WarrantyDTO;

public interface WarrantyService {
    void saveWarranty(WarrantyDTO warrantyDTO);
    WarrantyDTO findOneByProductLineId(long id);
    void deleteWarrantyByProductLineId(long id);
}
