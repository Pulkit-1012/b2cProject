package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;
import in.ongrid.b2cverification.service.OnGridAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OnGridAPIServiceImpl implements OnGridAPIService {

    @Value("${ongrid.api.base.url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Override
    public OngridIndividualCreateUpdateDTO callOnGridApi(OngridIndividualCreateUpdateDTO ongridIndividualCreateUpdateDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        HttpEntity<OngridIndividualCreateUpdateDTO> requestEntity = new HttpEntity<>(ongridIndividualCreateUpdateDTO, headers);
        ResponseEntity<OngridIndividualCreateUpdateDTO> response = restTemplate.postForEntity(url, requestEntity, OngridIndividualCreateUpdateDTO.class);
        return response.getBody();
    }
}
