package com.hau.service;

import com.hau.dto.CorporationDTO;

import java.util.List;

public interface CorporationService {
    CorporationDTO getAllCorporationInformation();
    void saveCorporation(CorporationDTO corporationDTO);
}
