package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.dto.response.VerificationCardDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;
import in.ongrid.b2cverification.model.entities.GDCVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//public interface BaseVerificationRepository extends JpaRepository<BaseVerification, Long> {
//    BaseVerification findByRequestId(long requestId);
//}

public interface BaseVerificationRepository extends JpaRepository<BaseVerification, Long> {
    BaseVerification findByRequestId(long requestId);
    List<BaseVerification> findByIndividualId(long individualId);
}
