package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.dto.response.VerificationCardDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;
import in.ongrid.b2cverification.model.entities.GDCVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

//public interface BaseVerificationRepository extends JpaRepository<BaseVerification, Long> {
//    BaseVerification findByRequestId(long requestId);
//}

public interface BaseVerificationRepository extends JpaRepository<BaseVerification, Long> {
    BaseVerification findByRequestId(long requestId);
    List<BaseVerification> findByIndividualId(long individualId);

    @Query(value = "SELECT ongrid_gdc_request_id FROM base_verification WHERE individual_id = :individualId AND id = :id", nativeQuery = true)
    Long findRequestIdByIndividualIdAndBaseVerificationId(@Param("individualId") Long individualId, @Param("id") long id);

}
