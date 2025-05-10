package in.ongrid.b2cverification.controllers;


import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.exceptions.ResourceNotFoundException;
import in.ongrid.b2cverification.exceptions.UnauthorizedException;
import in.ongrid.b2cverification.model.dto.PANDocDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.PANDocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class DocumentRestController {

    private JwtService jwtService;
    private UserRepository userRepository;
    private IndividualRepository individualRepository;
    private PANDocService panDocService;

    public DocumentRestController(JwtService jwtService, UserRepository userRepository, IndividualRepository individualRepository, PANDocService panDocService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.individualRepository = individualRepository;
        this.panDocService = panDocService;
    }


    //to add a pan number
    @PostMapping("/{userId}/individuals/{individualId}")
    public ResponseEntity<String> addPan(@PathVariable long userId, @PathVariable long individualId, @RequestHeader("Authorization") String token,@RequestBody PANDocDTO panDocDTO) {
        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
        Optional<Individual> dbIndividual = individualRepository.findById(individualId);
        Optional<User> dbUser = userRepository.findById(userId);

        if(dbUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found!");
        }
        if(!dbUser.get().getEmail().equals(emailFromToken)) {
            throw new UnauthorizedException("You do not have permission to add this document!");
        }

        if(dbIndividual.isEmpty()) {
            throw new ResourceNotFoundException("Individual not found!");
        }

        if(!dbIndividual.get().getAddedBy().equals(dbUser.get())) {
            throw new UnauthorizedException("You do not have permission to add this document!");
        }


        panDocService.savePanDoc(dbIndividual.get(), panDocDTO);

        return ResponseEntity.ok("Successfully added PAN document!");
    }
}
