package com.haui.service.impl;

import com.haui.converter.Converter;
import com.haui.dto.ProductLineDTO;
import com.haui.entity.BrandEntity;
import com.haui.entity.ProductLineEntity;
import com.haui.entity.WarrantyEntity;
import com.haui.repository.BrandRepository;
import com.haui.repository.ProductLineRepository;
import com.haui.repository.WarrantyRepository;
import com.haui.service.ProductLineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductLineServiceImpl implements ProductLineService {
    @Autowired
    private ProductLineRepository productLineRepository;
    @Autowired
    private Converter<ProductLineDTO,ProductLineEntity> productLineConverter;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private WarrantyRepository warrantyRepository;

    @Override
    public List<ProductLineDTO> findAllByBrandIdAndActive(Long brandId,boolean active) {
        List<ProductLineDTO> productLines = new ArrayList<>();
        List<ProductLineEntity> productLineEntities;
        if (brandId == 0){
            productLineEntities = productLineRepository.findAllByActive(active);
        }
        else{
            productLineEntities = productLineRepository.findAllByBrand_IdAndActive(brandId,active);
        }
        for(ProductLineEntity productLineEntity : productLineEntities){
            ProductLineDTO productLine = productLineConverter.convertToDTO(productLineEntity);
            productLines.add(productLine);
        }
        return productLines;
    }

    @Override
    public ProductLineDTO findByIdAndActive(long id,boolean active) {
        ProductLineEntity productLineEntity = productLineRepository.findByIdAndActive(id,active);
        return productLineConverter.convertToDTO(productLineEntity);
    }

    @Override
    public Page<ProductLineDTO> findAllByBrandId(Long brandId, int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductLineEntity> productLineEntities = productLineRepository.findAllByBrand_IdAndActive(brandId, true, pageable);
        List<ProductLineEntity> filteredList = productLineEntities.getContent().stream().filter(ProductLineEntity::isActive).toList();

        List<ProductLineDTO> productLineDTOs = filteredList.stream().map(productLineEntity -> {
            ProductLineDTO productLineDTO = productLineConverter.convertToDTO(productLineEntity);
            productLineDTO.setBrandName(productLineEntity.getBrand().getName());
            return productLineDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(productLineDTOs, pageable, productLineEntities.getTotalElements());
    }

    @Override
    public Page<ProductLineDTO> findAll(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductLineEntity> productLineEntities = productLineRepository.findAllByActive(true, pageable);
        List<ProductLineDTO> productLineDTOs = productLineEntities.getContent().stream().map(productLineEntity -> {
            ProductLineDTO productLineDTO = productLineConverter.convertToDTO(productLineEntity);
            productLineDTO.setBrandName(productLineEntity.getBrand().getName());
            return productLineDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(productLineDTOs, pageable, productLineEntities.getTotalElements());
    }

    @Override
    public Page<ProductLineDTO> findAllWithWarranty(int page, int limit, boolean isHasWarranty) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductLineEntity> productLineEntities;
        if(isHasWarranty){
            productLineEntities = productLineRepository.findByWarrantyIsNotNull(pageable);
        }
        else{
            productLineEntities = productLineRepository.findByWarrantyIsNull(pageable);
        }
        List<ProductLineDTO> productLineDTOs = productLineEntities.getContent().stream().map(productLineEntity -> {
            ProductLineDTO productLineDTO = productLineConverter.convertToDTO(productLineEntity);
            productLineDTO.setBrandName(productLineEntity.getBrand().getName());
            return productLineDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(productLineDTOs, pageable, productLineEntities.getTotalElements());
    }

    @Override
    public Page<ProductLineDTO> findAllWithWarrantyByBrandId(int page, int limit, boolean isHasWarranty, Long brandId) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductLineEntity> productLineEntities;
        if(isHasWarranty){
            productLineEntities = productLineRepository.findByBrand_IdAndWarrantyIsNotNull(brandId, pageable);
        }
        else{
            productLineEntities = productLineRepository.findByBrand_IdAndWarrantyIsNull(brandId, pageable);
        }
        List<ProductLineDTO> productLineDTOs = productLineEntities.getContent().stream().map(productLineEntity -> {
            ProductLineDTO productLineDTO = productLineConverter.convertToDTO(productLineEntity);
            productLineDTO.setBrandName(productLineEntity.getBrand().getName());
            return productLineDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(productLineDTOs, pageable, productLineEntities.getTotalElements());
    }

    @Transactional
    @Override
    public void save( ProductLineDTO productLineDTO) {
        ProductLineEntity productLineEntity = productLineConverter.convertToEntity(productLineDTO);
        BrandEntity brandEntity = brandRepository.findOneByIdAndActive(productLineDTO.getBrandId(),true);
        productLineEntity.setBrand(brandEntity);
        productLineEntity.setActive(true);
        if(productLineDTO.getId() != null){
            WarrantyEntity warrantyEntity = warrantyRepository.findByProductLineEntity_IdAndProductLineEntity_Active(productLineDTO.getId(), true);
            if(warrantyEntity != null){
                warrantyEntity.setProductLineEntity(productLineEntity);
                productLineEntity.setWarranty(warrantyEntity);
            }
        }
        productLineRepository.save(productLineEntity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        ProductLineEntity productLineEntity = productLineRepository.findByIdAndActive(id,true);
        if(productLineEntity != null){
            productLineEntity.setActive(false);
            productLineEntity.getProducts().forEach(productEntity -> {
                productEntity.setActive(false);
            });
            WarrantyEntity warrantyEntity = productLineEntity.getWarranty();
            if(warrantyEntity != null) {
                productLineEntity.setWarranty(null);
                warrantyRepository.delete(warrantyEntity);
            }
        }
        productLineRepository.save(productLineEntity);
    }

    @Override
    public Page<ProductLineDTO> findByKeyword(String keyword, int page, int limit) {
        if(StringUtils.isEmpty(keyword.trim())) {
            return findAll(page, limit);
        }
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<ProductLineEntity> productLineEntities = productLineRepository.findAllByNameContainingIgnoreCaseAndActive(keyword, true, pageable);
        List<ProductLineDTO> productLineDTOs = productLineEntities.getContent().stream().map(productLineEntity -> {
            ProductLineDTO productLineDTO = productLineConverter.convertToDTO(productLineEntity);
            productLineDTO.setBrandName(productLineEntity.getBrand().getName());
            return productLineDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(productLineDTOs, pageable, productLineEntities.getTotalElements());
    }
}
