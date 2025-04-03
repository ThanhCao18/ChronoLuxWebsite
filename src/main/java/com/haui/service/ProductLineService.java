package com.haui.service;

import com.haui.dto.ProductLineDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductLineService {
    List<ProductLineDTO> findAllByBrandIdAndActive(Long brandId,boolean active);
    ProductLineDTO findByIdAndActive(long id,boolean active);
    Page<ProductLineDTO> findAllByBrandId(Long brandId, int page, int limit);
    Page<ProductLineDTO> findAll(int page, int limit);
    Page<ProductLineDTO> findAllWithWarranty(int page, int limit, boolean isHasWarranty);
    Page<ProductLineDTO> findAllWithWarrantyByBrandId(int page, int limit, boolean isHasWarranty, Long brandId);
    void save(ProductLineDTO productLineDTO);
    void deleteById(Long id);
    Page<ProductLineDTO> findByKeyword(String keyword, int page, int limit);
}
