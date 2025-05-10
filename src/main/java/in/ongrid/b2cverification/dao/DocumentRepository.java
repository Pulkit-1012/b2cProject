package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.Document;
import in.ongrid.b2cverification.model.entities.PANDoc;
import in.ongrid.b2cverification.model.enums.DocType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByIndividualIdAndDocType(Long id, DocType docType);

    @Query(value = "SELECT ongrid_id FROM document WHERE individual_id = :individualId", nativeQuery = true)
    String findDocumentIdByIndividualId(@Param("individualId") long individualId);
}
