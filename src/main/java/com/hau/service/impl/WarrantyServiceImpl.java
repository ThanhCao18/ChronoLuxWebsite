package com.hau.service.impl;

import com.hau.converter.Converter;
import com.hau.dto.ProductLineDTO;
import com.hau.dto.WarrantyDTO;
import com.hau.entity.ProductLineEntity;
import com.hau.entity.WarrantyEntity;
import com.hau.repository.ProductLineRepository;
import com.hau.repository.WarrantyRepository;
import com.hau.service.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class WarrantyServiceImpl implements WarrantyService {
    @Autowired
    private WarrantyRepository warrantyRepository;
    @Autowired
    private ProductLineRepository productLineRepository;
    @Autowired
    private Converter<WarrantyDTO, WarrantyEntity> converter;

    @Transactional
    @Override
    public void saveWarranty(WarrantyDTO warrantyDTO) {
        WarrantyEntity warrantyEntity = converter.convertToEntity(warrantyDTO);

        ProductLineEntity productLineEntity = productLineRepository.findByIdWithWarranty(warrantyDTO.getProductLineId());
        if(productLineEntity.getWarranty() != null) {
            warrantyEntity.setProductLineEntity(productLineEntity);
            warrantyRepository.save(warrantyEntity);
        }
        else{
            warrantyEntity.setProductLineEntity(productLineEntity);
            productLineEntity.setWarranty(warrantyEntity);
            productLineRepository.save(productLineEntity);
        }
    }


    @Override
    public WarrantyDTO findOneByProductLineId(long id) {
        ProductLineEntity productLineEntity = productLineRepository.findByIdAndActive(id,true);
        WarrantyEntity warrantyEntity = warrantyRepository.findByProductLineEntity_IdAndProductLineEntity_Active(id, true);
        if(warrantyEntity == null) {
            warrantyEntity = new WarrantyEntity();
            warrantyEntity.setProductLineEntity(productLineEntity);
        }
        return converter.convertToDTO(warrantyEntity);
    }

    @Transactional
    @Override
    public void deleteWarrantyByProductLineId(long id) {
        ProductLineEntity productLineEntity = productLineRepository.findByIdAndActive(id,true);
        WarrantyEntity warrantyEntity = warrantyRepository.findOne(productLineEntity.getWarranty().getId());
        if (warrantyEntity != null) {
            productLineEntity.setWarranty(null);
            productLineRepository.save(productLineEntity);
        }
        warrantyRepository.delete(warrantyEntity);
    }
}
