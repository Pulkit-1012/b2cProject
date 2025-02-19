package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.dao.BaseVerificationRepository;
import in.ongrid.b2cverification.dao.DocumentRepository;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.PANVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.Document;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.PANVerification;
import in.ongrid.b2cverification.model.enums.OfferingType;
import in.ongrid.b2cverification.service.DocumentService;
import in.ongrid.b2cverification.service.OnGridAPIService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final IndividualRepository individualRepository;
    private final UserRepository userRepository;
    private final OnGridAPIService onGridAPIService;
    private final BaseVerificationRepository baseVerificationRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository, IndividualRepository individualRepository, UserRepository userRepository, OnGridAPIService onGridAPIService, BaseVerificationRepository baseVerificationRepository) {
        this.documentRepository = documentRepository;
        this.individualRepository = individualRepository;
        this.userRepository = userRepository;
        this.onGridAPIService = onGridAPIService;
        this.baseVerificationRepository = baseVerificationRepository;
    }

    @Override
    public void save(Document document) {
        documentRepository.save(document);
    }




    @Override
    public PANVerificationResponseDTO requestingPanVerification(long userId, long individualId, String documentId) {

        PANVerification baseverification = new PANVerification();

        Optional<Individual> individual = individualRepository.findById(individualId);

        long ogIndividualId = individual.get().getOnGridIndividualId();

        baseverification.setIndividual(individual.get());
        baseverification.setRequestedByUserId(userRepository.findById(userId).get());

        PANVerificationResponseDTO panVerificationResponseDTO = onGridAPIService.postPANVerification(documentId, ogIndividualId);

        baseverification.setRequestId(panVerificationResponseDTO.getRequestId());
        baseverification.setState(panVerificationResponseDTO.getState());
        if(panVerificationResponseDTO.getReport()!=null) {
            baseverification.setReason(panVerificationResponseDTO.getReport().getReason());
            baseverification.setResult(panVerificationResponseDTO.getReport().getResult());
            baseverification.setPdfServingUrl(panVerificationResponseDTO.getReport().getPdfServingUrl());
        }
        baseverification.setClosedReason(panVerificationResponseDTO.getClosedReason());
        baseverification.setOfferingType(OfferingType.PAN);
        baseverification.setClosedRemarks(panVerificationResponseDTO.getClosedRemarks());
        baseverification.setCreated(panVerificationResponseDTO.getCreated());
        baseverification.setDataSufficiencyDate(panVerificationResponseDTO.getDataSufficiencyDate());
        baseverification.setCompletedDate(panVerificationResponseDTO.getCompletedDate());
        baseverification.setClosed(panVerificationResponseDTO.getClosedDate());


        baseVerificationRepository.save(baseverification);
        return panVerificationResponseDTO;
    }
}
