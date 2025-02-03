package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.exceptions.BadRequestException;
import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.mappers.IndividualMapper;
import in.ongrid.b2cverification.model.dto.CreateIndividualRequest;
import in.ongrid.b2cverification.model.dto.OngridIndividualRequestDTO;
import in.ongrid.b2cverification.model.dto.response.IndividualDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.IndividualService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndividualServiceImpl implements IndividualService {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    private final IndividualRepository individualRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public IndividualServiceImpl(IndividualRepository theIndividualRepository, UserRepository theUserRepository, JwtService jwtService) {
        this.individualRepository = theIndividualRepository;
        this.userRepository = theUserRepository;
        this.jwtService = jwtService;
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
        individual.setIndividualName(individualDTO.getIndividualName());
        individual.setPhoneNumber(individualDTO.getPhoneNumber());
        individual.setFatherName(individualDTO.getFatherName());
        individual.setMotherName(individualDTO.getMotherName());
        individual.setAddress(individualDTO.getAddress());
        individual.setGender(individualDTO.getGender());
        individual.setDob(individualDTO.getDob());

        Individual savedIndividual = individualRepository.save(individual);






        //onboard individual to ongrid
        //save ongrid individualid (add a field in the entity)





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

//    @Override
//    public IndividualDTO findById(Long id) {
//        Optional<Individual> dbIndividual = individualRepository.findById(id);
//
//        if(dbIndividual.isPresent()) {
//            return IndividualMapper.toDTO(dbIndividual.get());
//        }
//        else throw new RuntimeException("Individual with id " + id + " not found");
//
//    }


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
        if(StringUtils.isBlank(request.getIndividualName())) throw new BadRequestException("Individual Name Required");
        else if(StringUtils.isBlank(request.getAddress())) throw new BadRequestException("Address Required");
        else if(StringUtils.isBlank(request.getFatherName())) throw new BadRequestException("Father Name Required");
        else if(StringUtils.isBlank(request.getMotherName())) throw new BadRequestException("Mother Name Required");
        else if(StringUtils.isBlank(request.getGender())) throw new BadRequestException("Gender Required");
        else if(request.getDob() == null) throw new BadRequestException("Dob Required");
        else if(String.valueOf(request.getPhoneNumber()).length() != 10) throw new BadRequestException("Phone Number should be exactly 10 digits!");

        Individual individual = new Individual();

        individual.setIndividualName(request.getIndividualName());
        individual.setPhoneNumber(request.getPhoneNumber());

        individualRepository.save(individual);
        return individual;
    }

    @Override
    public void deleteById(Long id) {
        individualRepository.deleteById(id);
    }



    @Override
    public long callOnGridApi(OngridIndividualRequestDTO ongridIndividualRequestDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        HttpEntity<OngridIndividualRequestDTO> requestEntity = new HttpEntity<>(ongridIndividualRequestDTO, headers);
        ResponseEntity<OngridIndividualRequestDTO> response = restTemplate.postForEntity(url)
    }


}
