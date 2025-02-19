package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.PANDocDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.PANDoc;

public interface PANDocService {
    PANDoc savePanDoc(Individual individual, PANDocDTO panDocDTO);
}
