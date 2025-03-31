package com.hau.service;

import com.hau.dto.WarrantyDTO;

public interface WarrantyService {
    void saveWarranty(WarrantyDTO warrantyDTO);
    WarrantyDTO findOneByProductLineId(long id);
    void deleteWarrantyByProductLineId(long id);
}
