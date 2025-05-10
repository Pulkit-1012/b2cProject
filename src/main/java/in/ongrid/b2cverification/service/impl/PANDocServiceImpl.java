package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.dao.PANDocRepository;
import in.ongrid.b2cverification.model.dto.PANDocDTO;
import in.ongrid.b2cverification.model.dto.response.AddPANDocResponseDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.PANDoc;
import in.ongrid.b2cverification.model.enums.DocType;
import in.ongrid.b2cverification.service.OnGridAPIService;
import in.ongrid.b2cverification.service.PANDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PANDocServiceImpl implements PANDocService {


    PANDocRepository panDocRepository;
    OnGridAPIService onGridAPIService;

    public PANDocServiceImpl(PANDocRepository thePanDocRepository, OnGridAPIService theOnGridAPIService) {
        this.panDocRepository = thePanDocRepository;
        this.onGridAPIService = theOnGridAPIService;
    }


    @Override
    public PANDoc savePanDoc(Individual individual, PANDocDTO panDocDTO) {

        PANDoc panDoc = new PANDoc();
        panDoc.setIndividual(individual);
        panDoc.setPanNumber(panDocDTO.getDocumentUID());
        panDoc.setDocType(DocType.PAN);

        // Calling OnGrid API to add PAN Doc
        AddPANDocResponseDTO addPANDocResponseDTO = onGridAPIService.addPANDocument(String.valueOf(individual.getOnGridIndividualId()), panDocDTO);
        panDoc.setOngridId(addPANDocResponseDTO.getId());

        return panDocRepository.save(panDoc);
    }
}
