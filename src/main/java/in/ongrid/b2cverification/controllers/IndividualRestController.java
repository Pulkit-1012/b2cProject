package in.ongrid.b2cverification.controllers;


import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;
import in.ongrid.b2cverification.model.dto.response.IndividualDTO;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.service.IndividualService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class IndividualRestController {


    private final IndividualService individualService;
    private final IndividualRepository individualRepository;

    public IndividualRestController(IndividualService theIndividualService, IndividualRepository individualRepository) {
        this.individualService = theIndividualService;
        this.individualRepository = individualRepository;
    }



    //creating an individual
    @PostMapping("/{userId}/individuals")
    public ResponseEntity<IndividualDTO> createIndividual(@PathVariable long userId, @RequestBody IndividualDTO individualDTO, @RequestHeader("Authorization") String token) {

        IndividualDTO savedIndividual = individualService.createIndividual(userId, individualDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIndividual);
    }


    //viewing an individual (user viewing his/her individual)
    @GetMapping("/{userId}/individuals/{individualId}")
    public ResponseEntity<IndividualDTO>getIndividual(@PathVariable long userId, @PathVariable long individualId, @RequestHeader("Authorization") String token) {
        IndividualDTO individualDTO = individualService.findById(userId, individualId, token);
        return ResponseEntity.ok(individualDTO);
    };



    //on-boarding an individual
    @PostMapping("/{userId}/individuals/{individualId}/onboard-individual")
    public ResponseEntity<OngridIndividualCreateUpdateDTO> onBoardIndividual(@PathVariable long userId, @PathVariable long individualId, @RequestHeader("Authorization") String token) {
        Optional<Individual> individual = individualRepository.findById(individualId);
        OngridIndividualCreateUpdateDTO ongridIndividualCreateUpdateDTO = individualService.onBoardIndividual(userId, individual.get(), token);
        return ResponseEntity.ok(ongridIndividualCreateUpdateDTO);
    }
}
