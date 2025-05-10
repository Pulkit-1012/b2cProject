package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.GDCVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GDCVerificationRepository extends JpaRepository<GDCVerification, Long> {
    GDCVerification findByRequestId(long requestId);
}
//
//public interface GDCVerificationRepository extends BaseVerificationRepository {
//
//}
