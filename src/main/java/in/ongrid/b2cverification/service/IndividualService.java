package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.CreateIndividualRequest;
import in.ongrid.b2cverification.model.dto.response.IndividualDTO;
import in.ongrid.b2cverification.model.entities.Individual;

import java.util.List;


public interface IndividualService {
    List<Individual> findAll();
    IndividualDTO findById(long id, long individualId, String token);
    Individual save(CreateIndividualRequest request);
    void deleteById(Long id);

    IndividualDTO createIndividual(long userID, IndividualDTO individualDTO, String token);
}
