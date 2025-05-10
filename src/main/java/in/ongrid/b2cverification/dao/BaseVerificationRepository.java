package in.ongrid.b2cverification.dao;

import in.ongrid.b2cverification.model.entities.BaseVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseVerificationRepository extends JpaRepository<BaseVerification, Long> {
}
