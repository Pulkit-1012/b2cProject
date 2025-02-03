package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
}
