package com.haui.service.impl;

import com.haui.converter.Converter;
import com.haui.dto.CorporationDTO;
import com.haui.entity.CorporationEntity;
import com.haui.repository.CorporationRepository;
import com.haui.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CorporationServiceImpl implements CorporationService {
    private final CorporationRepository corporationRepository;
    private final Converter<CorporationDTO, CorporationEntity> converter;

    @Autowired
    public CorporationServiceImpl(CorporationRepository corporationRepository, Converter<CorporationDTO, CorporationEntity> converter) {
        this.corporationRepository = corporationRepository;
        this.converter = converter;
    }

    @Override
    public CorporationDTO getAllCorporationInformation() {
       List<CorporationEntity> list = corporationRepository.findAll();

        if (list.isEmpty()) {
            return new CorporationDTO();
        }

        return converter.convertToDTO(list.getFirst());
    }

    @Transactional
    @Override
    public void saveCorporation(CorporationDTO corporationDTO) {
        CorporationEntity corporationEntity = converter.convertToEntity(corporationDTO);
        corporationRepository.save(corporationEntity);
    }
}
