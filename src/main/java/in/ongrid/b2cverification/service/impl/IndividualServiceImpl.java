package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.mappers.IndividualMapper;
import in.ongrid.b2cverification.model.dto.CreateIndividualRequest;
import in.ongrid.b2cverification.model.dto.response.IndividualDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.IndividualService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IndividualServiceImpl implements IndividualService {

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
//        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        User user  = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User Not Found"));
//        Optional<User> user = userRepository.findById(userID);

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
        return IndividualMapper.toDTO(savedIndividual);
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

        if(dbUser.isEmpty() || !dbUser.get().getEmail().equals(emailFromToken)) {
            throw new RuntimeException("User Not Found OR Unauthorized access");
        }

        Optional<Individual> individual = individualRepository.findById(individualId);

        if(individual.isEmpty() || !individual.get().getAddedBy().equals(dbUser.get())) {
            throw new RuntimeException("Individual Not Found OR User does not own this individual!");
        }

        return IndividualMapper.toDTO(individual.get());
    }
    //not working will see tomorrow



    @Override
    public Individual save(CreateIndividualRequest request) {
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


}
