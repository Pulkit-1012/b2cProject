package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;
import in.ongrid.b2cverification.service.OnGridAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OnGridAPIServiceImpl implements OnGridAPIService {

    @Value("${ongrid.api.base.url}")
    private String url;

    @Value("${ongrid.gdcverification.request.base.url}")
    private String gdcUrl;

    @Value("${ongrid.gdcverification.result.base.url}")
    private String gdcResultUrl;

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





    @Override
    public BaseVerificationResponseDTO postGDCVerification(String individualId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        HttpEntity<String> requestEntity = new HttpEntity<>(individualId, headers);
        String url = String.format(gdcUrl, individualId);
        ResponseEntity<BaseVerificationResponseDTO> response = restTemplate.postForEntity(url, requestEntity, BaseVerificationResponseDTO.class);
        return response.getBody();
    }



//    @Override
//    public GDCVerificationResponseDTO getGDCVerification(String individualId, long requestId) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = UriComponentsBuilder.fromHttpUrl(gdcResultUrl)
//                .queryParam("requestId", requestId)
//                .buildAndExpand(individualId)
//                .toUriString();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBasicAuth(username, password);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<GDCVerificationResponseDTO> response = restTemplate.exchange(
//                url, HttpMethod.GET, requestEntity, GDCVerificationResponseDTO.class);
//
//        return response.getBody();
//    }

    @Override
    public BaseVerificationResponseDTO getGDCVerification(String individualId, long requestId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(gdcResultUrl)
                .queryParam("requestId", requestId)
                .buildAndExpand(individualId)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<BaseVerificationResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, BaseVerificationResponseDTO.class);

        return response.getBody();
    }
}