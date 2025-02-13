package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;

public interface OnGridAPIService {
    OngridIndividualCreateUpdateDTO callOnGridApi(OngridIndividualCreateUpdateDTO ongridIndividualCreateUpdateDTO);
    BaseVerificationResponseDTO postGDCVerification(String individualId);
}
