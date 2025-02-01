package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.model.dto.CreateIndividualRequest;
import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.service.IndividualService;

import java.util.List;
import java.util.Optional;

public class IndividualServiceImpl implements IndividualService {

    private final IndividualRepository individualRepository;

    public IndividualServiceImpl(IndividualRepository theIndividualRepository) {
        this.individualRepository = theIndividualRepository;
    }


    @Override
    public List<Individual> findAll() {
        return individualRepository.findAll();
    }

    @Override
    public Individual findById(Long id) {
        Optional<Individual> dbIndividual = individualRepository.findById(id);

        if(dbIndividual.isPresent()) {
            return dbIndividual.get();
        }
        else throw new RuntimeException("Individual with id " + id + " not found");

    }

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
