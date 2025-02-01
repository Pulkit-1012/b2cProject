package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.CreateIndividualRequest;
import in.ongrid.b2cverification.model.dto.CreateUserRequest;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IndividualService {
    List<Individual> findAll();
    Individual findById(Long id);
    Individual save(CreateIndividualRequest request);
    void deleteById(Long id);
}
