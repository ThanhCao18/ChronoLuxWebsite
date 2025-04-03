package com.haui.service;

import com.haui.dto.CorporationDTO;

public interface CorporationService {
    CorporationDTO getAllCorporationInformation();
    void saveCorporation(CorporationDTO corporationDTO);
}
