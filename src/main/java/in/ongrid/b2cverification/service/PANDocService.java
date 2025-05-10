package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.PANDocDTO;
import in.ongrid.b2cverification.model.entities.Individual;

public interface PANDocService {
    void savePanDoc(Individual individual, PANDocDTO panDocDTO);
}
