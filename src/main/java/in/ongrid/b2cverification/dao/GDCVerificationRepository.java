package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.GDCVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GDCVerificationRepository extends JpaRepository<GDCVerification, Long> {
    Optional<Object> findByRequestId(long requestId);
}
