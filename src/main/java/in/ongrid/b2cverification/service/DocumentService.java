package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.response.PANVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.Document;

public interface DocumentService {
    void save(Document document);

    PANVerificationResponseDTO requestingPanVerification(long userId, long individualId, String documentId);
}
