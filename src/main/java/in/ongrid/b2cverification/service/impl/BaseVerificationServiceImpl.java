package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.BaseVerificationRepository;
import in.ongrid.b2cverification.dao.GDCVerificationRepository;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.mappers.VerificationMapper;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.VerificationCardDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;
import in.ongrid.b2cverification.model.entities.GDCVerification;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.model.enums.OfferingType;
import in.ongrid.b2cverification.model.enums.State;
import in.ongrid.b2cverification.service.BaseVerificationService;
import in.ongrid.b2cverification.service.GDCVerificationService;
import in.ongrid.b2cverification.service.OnGridAPIService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BaseVerificationServiceImpl implements BaseVerificationService {

    private final BaseVerificationRepository baseVerificationRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final IndividualRepository individualRepository;
    private final OnGridAPIService onGridAPIService;
    private final GDCVerificationService gdcVerificationService;
    private final GDCVerificationRepository gdcVerificationRepository;

    public BaseVerificationServiceImpl(BaseVerificationRepository baseVerificationRepository, JwtService jwtService, UserRepository userRepository, IndividualRepository individualRepository, OnGridAPIService onGridAPIService, GDCVerificationService gdcVerificationService, GDCVerificationRepository gdcVerificationRepository) {
        this.baseVerificationRepository = baseVerificationRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.individualRepository = individualRepository;
        this.onGridAPIService = onGridAPIService;
        this.gdcVerificationService = gdcVerificationService;
        this.gdcVerificationRepository = gdcVerificationRepository;
    }


    @Override
    public List<BaseVerification> findAll() {
        return baseVerificationRepository.findAll();
    }

    @Override
    public BaseVerification save(BaseVerification baseVerification) {
        return baseVerificationRepository.save(baseVerification);
    }


    @Override
    public BaseVerification findById(long id) {
        return baseVerificationRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(long id) {
        baseVerificationRepository.deleteById(id);
    }

    @Override
    public BaseVerification findByRequestId(long requestId) {
        return baseVerificationRepository.findByRequestId(requestId);
    }



    @Override
    public BaseVerificationResponseDTO requestGDCverification(long userId, long individualId, String token) {
        //checking if user exists or not
        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if(!user.getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("Unauthorized Request");
        }

        Optional<Individual> individual = individualRepository.findById(individualId);

        if(individual.isEmpty()) throw new ResourceNotFoundException("Individual Not Found");



        //getting the individualId saved after onboarding the individual
        long ogiId = individual.get().getOnGridIndividualId();

        //casting long to string
        String ogiIdString = Long.toString(ogiId);


        GDCVerification baseVerification = new GDCVerification();
        baseVerification.setRequestedByUserId(user);
        baseVerification.setIndividual(individual.get());


        //now i wil call the function to initiate the gdc verification api
        BaseVerificationResponseDTO baseVerificationResponseDTO = onGridAPIService.postGDCVerification(ogiIdString);


        //now that i have got my response, i will set values from this dto in the baseverification entityt
        baseVerification.setRequestId(baseVerificationResponseDTO.getRequestId());//request id is what we get along with the response of dgcverification-post wala
        baseVerification.setOfferingType(OfferingType.GDC);
        baseVerification.setState(baseVerificationResponseDTO.getState());
        baseVerification.setClosedReason(baseVerificationResponseDTO.getClosedReason());
        baseVerification.setClosedRemarks(baseVerificationResponseDTO.getClosedRemarks());
        baseVerification.setDataSufficiencyDate(baseVerificationResponseDTO.getDataSufficiencyDate());
        baseVerification.setCompletedDate(baseVerificationResponseDTO.getCompletedDate());
        baseVerification.setClosed(baseVerificationResponseDTO.getClosed());





        if( baseVerificationResponseDTO.getGdcReport() != null) {
            baseVerification.setResult(baseVerificationResponseDTO.getGdcReport().getResult());
        }

        baseVerificationRepository.save(baseVerification);
        return baseVerificationResponseDTO;
    }




    @Override
    public BaseVerificationResponseDTO checkGDCVerificationStatus(long userId, long individualId, String token,
                                                                 long requestId) {

        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if(!user.getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("Unauthorized Request");
        }

        Optional<Individual> individual = individualRepository.findById(individualId);

        if(individual.isEmpty()) throw new ResourceNotFoundException("Individual Not Found");


        GDCVerification baseVerification = gdcVerificationRepository.findByRequestId(requestId);


        long onGridIndividualId = individual.get().getOnGridIndividualId();
        String onGridIndividualIdString = Long.toString(onGridIndividualId);


        BaseVerificationResponseDTO baseVerificationResponseDTO = onGridAPIService.getGDCVerification(onGridIndividualIdString, requestId);


        baseVerification.setState(baseVerificationResponseDTO.getState());
        baseVerification.setClosedReason(baseVerificationResponseDTO.getClosedReason());
        baseVerification.setClosedRemarks(baseVerificationResponseDTO.getClosedRemarks());
        baseVerification.setDataSufficiencyDate(baseVerificationResponseDTO.getDataSufficiencyDate());
        baseVerification.setCompletedDate(baseVerificationResponseDTO.getCompletedDate());
        baseVerification.setClosed(baseVerificationResponseDTO.getClosed());

        if( baseVerificationResponseDTO.getGdcReport() != null) {
            baseVerification.setResult(baseVerificationResponseDTO.getGdcReport().getResult());
            baseVerification.setReason(baseVerificationResponseDTO.getGdcReport().getReason());
            baseVerification.setPdfServingUrl(baseVerificationResponseDTO.getGdcReport().getPdfServingUrl());
        }


        baseVerificationRepository.save(baseVerification);
        return baseVerificationResponseDTO;
    }

    @Override
    public List<VerificationCardDTO> getVerificationList(long userId, long individualId, String token) {

        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if(!user.getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("Unauthorized Request");
        }

        Optional<Individual> individual = individualRepository.findById(individualId);

        if(individual.isEmpty()) throw new ResourceNotFoundException("Individual Not Found");

//        if(!individual.get().getAddedBy().equals(userId)) throw new UnauthorizedException("Unauthorized Request");

        List<BaseVerification> baseVerifications = baseVerificationRepository.findByIndividualId(individualId);

        return baseVerifications.stream().map(VerificationMapper::toDto).toList();
    }


}
