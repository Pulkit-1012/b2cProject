package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.*;
import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.PANVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.*;
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
    private final JwtService jwtService;
    private final PANVerificationRepository panVerificationRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository, IndividualRepository individualRepository, UserRepository userRepository, OnGridAPIService onGridAPIService, BaseVerificationRepository baseVerificationRepository, JwtService jwtService, PANVerificationRepository panVerificationRepository) {
        this.documentRepository = documentRepository;
        this.individualRepository = individualRepository;
        this.userRepository = userRepository;
        this.onGridAPIService = onGridAPIService;
        this.baseVerificationRepository = baseVerificationRepository;
        this.jwtService = jwtService;
        this.panVerificationRepository = panVerificationRepository;
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

    @Override
    public PANVerificationResponseDTO checkPANVerificationStatus(long userId, long individualId, long id, String token) {

        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if(!user.getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("Unauthorized Request");
        }

        Optional<Individual> individual = individualRepository.findById(individualId);

        if(individual.isEmpty()) throw new ResourceNotFoundException("Individual Not Found");

        Long requestId = baseVerificationRepository.findRequestIdByIndividualIdAndBaseVerificationId(individualId, id);

        PANVerification baseVerification = panVerificationRepository.findByRequestId(requestId);

        long onGridIndividualId = individual.get().getOnGridIndividualId();

        PANVerificationResponseDTO panVerificationResponseDTO = onGridAPIService.getPANVerification(onGridIndividualId, requestId);

        baseVerification.setState(panVerificationResponseDTO.getState());
        baseVerification.setClosedReason(panVerificationResponseDTO.getClosedReason());
        baseVerification.setClosedRemarks(panVerificationResponseDTO.getClosedRemarks());
        baseVerification.setDataSufficiencyDate(panVerificationResponseDTO.getDataSufficiencyDate());
        baseVerification.setCompletedDate(panVerificationResponseDTO.getCompletedDate());
        baseVerification.setClosed(panVerificationResponseDTO.getClosedDate());

        if(panVerificationResponseDTO.getReport()!=null) {
            baseVerification.setReason(panVerificationResponseDTO.getReport().getReason());
            baseVerification.setResult(panVerificationResponseDTO.getReport().getResult());
            baseVerification.setPdfServingUrl(panVerificationResponseDTO.getReport().getPdfServingUrl());
        }


        panVerificationRepository.save(baseVerification);

        return panVerificationResponseDTO;
    }
}
