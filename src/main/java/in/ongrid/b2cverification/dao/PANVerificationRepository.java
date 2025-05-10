package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.PANVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PANVerificationRepository extends JpaRepository<PANVerification, Long> {
    PANVerification findByRequestId(long requestId);
}
