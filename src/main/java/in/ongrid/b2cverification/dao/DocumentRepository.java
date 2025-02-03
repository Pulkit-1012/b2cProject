package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.Document;
import in.ongrid.b2cverification.model.entities.PANDoc;
import in.ongrid.b2cverification.model.enums.DocType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByIndividualIdAndDocType(Long id, DocType docType);
}
