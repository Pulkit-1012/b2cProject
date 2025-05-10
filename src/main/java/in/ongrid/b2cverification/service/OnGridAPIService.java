package in.ongrid.b2cverification.service;

import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;

public interface OnGridAPIService {
    OngridIndividualCreateUpdateDTO callOnGridApi(OngridIndividualCreateUpdateDTO ongridIndividualCreateUpdateDTO);
}
