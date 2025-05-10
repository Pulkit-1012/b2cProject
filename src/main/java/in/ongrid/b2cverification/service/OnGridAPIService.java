package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;
import in.ongrid.b2cverification.model.dto.PANDocDTO;
import in.ongrid.b2cverification.model.dto.response.AddPANDocResponseDTO;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.PANVerificationResponseDTO;

public interface OnGridAPIService {
    OngridIndividualCreateUpdateDTO callOnGridApi(OngridIndividualCreateUpdateDTO ongridIndividualCreateUpdateDTO);
    BaseVerificationResponseDTO postGDCVerification(String individualId);
//    GDCVerificationResponseDTO getGDCVerification(String individualId, long requestId);
    BaseVerificationResponseDTO getGDCVerification(String individualId, long requestId);

    AddPANDocResponseDTO addPANDocument(String individualId, PANDocDTO panDocDTO);

    PANVerificationResponseDTO postPANVerification(String documentId, long ogIndividualId);

    PANVerificationResponseDTO getPANVerification(long individualId, long requestId);
}
