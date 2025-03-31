package com.hau.service.impl;

import com.hau.converter.BrandConverter;
import com.hau.dto.BrandDTO;
import com.hau.entity.BrandEntity;
import com.hau.repository.BrandRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements com.hau.service.BrandService {
    @Autowired
    private BrandConverter brandConverter;
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandDTO> findAllByActive(boolean active) {
        List<BrandDTO> brands = new ArrayList<>();
        List<BrandEntity> brandEntities = brandRepository.findAllByActive(active);
        for (BrandEntity brandEntity : brandEntities) {
            BrandDTO brand = brandConverter.toDTO(brandEntity);
            brands.add(brand);
        }
        return brands;
    }

    public Page<BrandDTO> findAll(int page, int limit) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<BrandEntity> brandEntities = brandRepository.findAllByActive(true, pageable);
        return brandEntities.map(brandEntity -> brandConverter.toDTO(brandEntity));
    }

    @Override
    public BrandDTO getBrandById(Long id,boolean active) {
        BrandEntity brandEntity = brandRepository.findOneByIdAndActive(id,active);
        return brandConverter.toDTO(brandEntity);
    }

    @Transactional
    @Override
    public void saveBrand(BrandDTO brandDTO) {
        BrandEntity brandEntity = brandConverter.toEntity(brandDTO);
        brandEntity.setActive(true);
        brandRepository.save(brandEntity);
    }

    @Transactional
    @Override
    public void deleteBrandById(Long id) {
        BrandEntity brandEntity = brandRepository.findOne(id);
        if (brandEntity != null) {
            Hibernate.initialize(brandEntity.getProductLines());
            brandEntity.setActive(false);

            brandEntity.getProductLines().forEach(productLine -> {
                productLine.setActive(false);
                Hibernate.initialize(productLine.getProducts());
                productLine.getProducts().forEach(product -> product.setActive(false));
            });

            brandRepository.save(brandEntity);
        }
    }

    @Override
    public Page<BrandDTO> findByKeyword(String keyword, int page, int limit) {
        if(StringUtils.isEmpty(keyword.trim())) {
            return findAll(page, limit);
        }
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<BrandEntity> brandEntities = brandRepository.findAllByNameContainingIgnoreCaseAndActive(keyword, true, pageable);
        return brandEntities.map(brandEntity -> brandConverter.toDTO(brandEntity));
    }
}

