package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.GDCVerification;

public interface GDCVerificationService {
    void save(GDCVerification gdcVerification);
//    GDCVerificationResponseDTO checkGDCVerificationStatus(long userId, long individualId, String token, long requestId);

}
