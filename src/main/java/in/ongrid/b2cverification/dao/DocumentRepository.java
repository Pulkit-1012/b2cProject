package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
