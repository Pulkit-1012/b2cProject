package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.BaseVerificationRepository;
import in.ongrid.b2cverification.dao.GDCVerificationRepository;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;
import in.ongrid.b2cverification.model.entities.GDCVerification;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.GDCVerificationService;
import in.ongrid.b2cverification.service.IndividualService;
import in.ongrid.b2cverification.service.OnGridAPIService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GDCVerificationServiceImpl implements GDCVerificationService {

    private final GDCVerificationRepository gdcVerificationRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final IndividualRepository individualRepository;
    private final BaseVerificationRepository baseVerificationRepository;
    private final OnGridAPIService onGridAPIService;

    public GDCVerificationServiceImpl(GDCVerificationRepository gdcVerificationRepository, JwtService jwtService, UserRepository userRepository, IndividualRepository individualRepository, BaseVerificationRepository baseVerificationRepository, OnGridAPIService onGridAPIService) {
        this.gdcVerificationRepository = gdcVerificationRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.individualRepository = individualRepository;
        this.baseVerificationRepository = baseVerificationRepository;
        this.onGridAPIService = onGridAPIService;
    }


    @Override
    public void save(GDCVerification gdcVerification) {
        gdcVerificationRepository.save(gdcVerification);
    }

//    @Override
//    public GDCVerificationResponseDTO checkGDCVerificationStatus(long userId, long individualId, String token,
//                                                                 long requestId) {
//
//        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
//
//        if (!user.getEmail().equals(emailFromToken)) {
//            throw new UnauthorizedException("Unauthorized Request");
//        }
//
//        Optional<Individual> individual = individualRepository.findById(individualId);
//
//        if (individual.isEmpty()) throw new ResourceNotFoundException("Individual Not Found");
//
//        BaseVerification baseVerification = baseVerificationRepository.findByRequestId(requestId);
//        long onGridIndividualId = individual.get().getOnGridIndividualId();
//        String onGridIndividualIdString = Long.toString(onGridIndividualId);
//
//        BaseVerificationResponseDTO baseVerificationResponseDTO = onGridAPIService.getGDCVerification(onGridIndividualIdString, requestId);
//
//        baseVerification.setState(baseVerificationResponseDTO.getState());
//        baseVerification.setClosedReason(baseVerificationResponseDTO.getClosedReason());
//        baseVerification.setClosedRemarks(baseVerificationResponseDTO.getClosedRemarks());
//        baseVerification.setDataSufficiencyDate(baseVerificationResponseDTO.getDataSufficiencyDate());
//        baseVerification.setCompletedDate(baseVerificationResponseDTO.getCompletedDate());
//        baseVerification.setClosed(baseVerificationResponseDTO.getClosed());
//
//        GDCVerificationResponseDTO gdcReport = baseVerificationResponseDTO.getGdcReport();
//
//        if (gdcReport != null) {
//            GDCVerification gdcVerification = new GDCVerification();
//            gdcVerification.setResult(gdcReport.getResult());
//            gdcVerification.setReason(gdcReport.getReason());
//            gdcVerification.setPdfServingUrl(gdcReport.getPdfServingUrl());
//
//            gdcVerificationRepository.save(gdcVerification);
//            baseVerificationRepository.save(baseVerification);
//        }
//
//        return gdcReport;
//    }

//    public GDCVerificationResponseDTO checkGDCVerificationStatus(long userId, long individualId, String token,
//                                                                 long requestId) {
//
//        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
//        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
//
//        if(!user.getEmail().equals(emailFromToken)) {
//            throw new UnauthorizedException("Unauthorized Request");
//        }
//
//        Optional<Individual> individual = individualRepository.findById(individualId);
//
//        if(individual.isEmpty()) throw new ResourceNotFoundException("Individual Not Found");
//
//
//        BaseVerification baseVerification = baseVerificationRepository.findByRequestId(requestId);
//
//
//        long onGridIndividualId = individual.get().getOnGridIndividualId();
//        String onGridIndividualIdString = Long.toString(onGridIndividualId);
//
//
//        BaseVerificationResponseDTO baseVerificationResponseDTO = onGridAPIService.getGDCVerification(onGridIndividualIdString, requestId);
//
//
//        baseVerification.setState(baseVerificationResponseDTO.getState());
//        baseVerification.setClosedReason(baseVerificationResponseDTO.getClosedReason());
//        baseVerification.setClosedRemarks(baseVerificationResponseDTO.getClosedRemarks());
//        baseVerification.setDataSufficiencyDate(baseVerificationResponseDTO.getDataSufficiencyDate());
//        baseVerification.setCompletedDate(baseVerificationResponseDTO.getCompletedDate());
//        baseVerification.setClosed(baseVerificationResponseDTO.getClosed());
//
//        GDCVerification gdcVerification = new GDCVerification();
//        GDCVerificationResponseDTO gdcReport = baseVerificationResponseDTO.getGdcReport();
//        if (gdcReport != null) {
//            gdcVerification.setResult(gdcReport.getResult());
//            gdcVerification.setReason(gdcReport.getReason());
//            gdcVerification.setPdfServingUrl(gdcReport.getPdfServingUrl());
//        }
//
//
//
//        gdcVerificationRepository.save(gdcVerification);
//        baseVerificationRepository.save(baseVerification);
//        return gdcReport;
//    }
}
