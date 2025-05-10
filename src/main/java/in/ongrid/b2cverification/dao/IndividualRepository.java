package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.Individual;
import in.ongrid.b2cverification.model.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
    List<Individual> findByAddedBy(User user);
    List<Individual> findByAddedByAndIsDeletedFalse(User user);//added now


}
