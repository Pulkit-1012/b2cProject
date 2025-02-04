package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.exceptions.BadRequestException;
import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.mappers.IndividualMapper;
import in.ongrid.b2cverification.model.dto.CreateIndividualRequest;
import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;
import in.ongrid.b2cverification.model.dto.response.IndividualDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.DataFormatterService;
import in.ongrid.b2cverification.service.IndividualService;
import in.ongrid.b2cverification.service.OnGridAPIService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndividualServiceImpl implements IndividualService {


    private final IndividualRepository individualRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OnGridAPIService onGridAPIService;
    private final DataFormatterService dataFormatterService;


    public IndividualServiceImpl(IndividualRepository theIndividualRepository, UserRepository theUserRepository, JwtService jwtService, OnGridAPIService onGridAPIService, DataFormatterService dataFormatterService) {
        this.individualRepository = theIndividualRepository;
        this.userRepository = theUserRepository;
        this.jwtService = jwtService;
        this.onGridAPIService = onGridAPIService;
        this.dataFormatterService = dataFormatterService;
    }



    //creating an individual
    @Override
    public IndividualDTO createIndividual(long userID, IndividualDTO individualDTO, String token) {
        //checking if user exists or not
        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        User user  = userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if(!user.getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("Unauthorized Request");
        }

        Individual individual = new Individual();
        individual.setAddedBy(user);
        individual.setName(individualDTO.getName());
        individual.setCity(individualDTO.getCity());
        individual.setGender(individualDTO.getGender());
        individual.setPhone(individualDTO.getPhone());
        individual.setProfessionId(individualDTO.getProfessionId());
        individual.setDob(individualDTO.getDob());
        individual.setHasConsent(true);
        individual.setConsentText(individualDTO.getConsentText());
        individual.setFathersName(individualDTO.getFathersName());
        individual.setMothersName(individualDTO.getMothersName());


//        Individual savedIndividual = individualRepository.save(individual);


        //some values from here, rest of the values are hard coded
        //inserting values into the OngridIndividualCreateUpdateDTO
        OngridIndividualCreateUpdateDTO ongridIndividualCreateUpdateDTO = new OngridIndividualCreateUpdateDTO();
        ongridIndividualCreateUpdateDTO.setName(individualDTO.getName());
        ongridIndividualCreateUpdateDTO.setCity(individualDTO.getCity());
        ongridIndividualCreateUpdateDTO.setGender(individualDTO.getGender());
        ongridIndividualCreateUpdateDTO.setPhone(individualDTO.getPhone());
        ongridIndividualCreateUpdateDTO.setProfessionId(individualDTO.getProfessionId());
        ongridIndividualCreateUpdateDTO.setDob(dataFormatterService.localDateToString(individualDTO.getDob()));
        ongridIndividualCreateUpdateDTO.setHasConsent(true);
        ongridIndividualCreateUpdateDTO.setConsentText(individualDTO.getConsentText());
        ongridIndividualCreateUpdateDTO.setFathersName(individualDTO.getFathersName());

        //


        individual.setOnGridIndividualId(onGridAPIService.callOnGridApi(ongridIndividualCreateUpdateDTO).getOnGridIndividualId());

        //onboard individual to ongrid
        //save ongrid individualid (add a field in the entity)




        Individual savedIndividual = individualRepository.save(individual);

        return IndividualMapper.toDTO(savedIndividual);
    }



    @Override
    public List<IndividualDTO> getIndividualsByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);

        List<Individual> individuals = individualRepository.findByAddedBy(user.get());


        return individuals.stream().map(IndividualMapper::toDTO).collect(Collectors.toList());
    }



    @Override
    public List<Individual> findAll() {
        return individualRepository.findAll();
    }



    //this function fetched the individual from database then checks if the userid passed as a pathvariable belong to the owner of this indoivuduak
    @Override
    public IndividualDTO findById(long userId, long individualId, String token) {

        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());

        Optional<User> dbUser = userRepository.findById(userId);

        if(dbUser.isEmpty()) {
            throw new ResourceNotFoundException("User Not Found OR Unauthorized access");
        }
        if(!dbUser.get().getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("Unauthorized Request");
        }

        Optional<Individual> individual = individualRepository.findById(individualId);

        if(individual.isEmpty()) {
            throw new ResourceNotFoundException("Individual Not Found OR User does not own this individual!");
        }
        if(!individual.get().getAddedBy().equals(dbUser.get())) {
            throw new UnauthorizedException("Unauthorized Request");
        }

        return IndividualMapper.toDTO(individual.get());
    }



    @Override
    public Individual save(CreateIndividualRequest request) {

        //handling exceptions
        if(StringUtils.isBlank(request.getName())) throw new BadRequestException("Individual Name Required");
        else if(StringUtils.isBlank(request.getCity())) throw new BadRequestException("Address Required");
        else if(StringUtils.isBlank(request.getFathersName())) throw new BadRequestException("Father Name Required");
        else if(StringUtils.isBlank(request.getMothersName())) throw new BadRequestException("Mother Name Required");
        else if(StringUtils.isBlank(request.getGender())) throw new BadRequestException("Gender Required");
        else if(request.getDob() == null) throw new BadRequestException("Dob Required");
        else if(request.getPhone().length() != 10) throw new BadRequestException("Phone Number should be exactly 10 digits!");

        Individual individual = getIndividual(request);

        individualRepository.save(individual);
        return individual;
    }

    private static Individual getIndividual(CreateIndividualRequest request) {
        Individual individual = new Individual();

        individual.setName(request.getName());
        individual.setCity(request.getCity());
        individual.setGender(request.getGender());
        individual.setPhone(request.getPhone());
        individual.setProfessionId(request.getProfessionId());
        individual.setHasConsent(true);
        individual.setConsentText(request.getConsentText());
        individual.setFathersName(request.getFathersName());
        individual.setMothersName(request.getMothersName());
        return individual;
    }

    @Override
    public void deleteById(Long id) {
        individualRepository.deleteById(id);
    }



}
