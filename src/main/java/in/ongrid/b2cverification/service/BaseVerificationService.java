package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;

import java.util.List;

public interface BaseVerificationService {
    List<BaseVerification>findAll();
    BaseVerification save(BaseVerification baseVerification);
    BaseVerification findById(long id);
    void delete(long id);
    BaseVerificationResponseDTO requestGDCverification(long userId, long individualId, String token);
//    GDCVerificationResponseDTO checkGDCVerificationStatus(long userId, long individualId, String token, long requestId);
    BaseVerification findByRequestId(long requestId);

    BaseVerificationResponseDTO checkGDCVerificationStatus(long userId, long individualId, String token, long requestId);
}
