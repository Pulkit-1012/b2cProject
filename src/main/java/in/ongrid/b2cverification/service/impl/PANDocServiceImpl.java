package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.dao.PANDocRepository;
import in.ongrid.b2cverification.model.dto.PANDocDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.PANDoc;
import in.ongrid.b2cverification.model.enums.DocType;
import in.ongrid.b2cverification.service.PANDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PANDocServiceImpl implements PANDocService {


    PANDocRepository panDocRepository;

    public PANDocServiceImpl(PANDocRepository thePanDocRepository) {
        this.panDocRepository = thePanDocRepository;
    }


    @Override
    public void savePanDoc(Individual individual, PANDocDTO panDocDTO) {

        PANDoc panDoc = new PANDoc();
        panDoc.setIndividual(individual);
        panDoc.setPanNumber(panDocDTO.getPanNumber());
        panDoc.setDocType(DocType.PAN);
        panDocRepository.save(panDoc);
    }
}
