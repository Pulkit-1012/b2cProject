package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.GDCVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GDCVerificationRepository extends JpaRepository<GDCVerification, Long> {
}
