package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.PANDoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PANDocRepository extends JpaRepository<PANDoc, Long> {
}
