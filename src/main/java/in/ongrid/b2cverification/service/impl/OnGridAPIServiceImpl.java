package in.ongrid.b2cverification.service.impl;

import in.ongrid.b2cverification.model.dto.OngridIndividualCreateUpdateDTO;
import in.ongrid.b2cverification.model.dto.PANBodyDTO;
import in.ongrid.b2cverification.model.dto.PANDocDTO;
import in.ongrid.b2cverification.model.dto.response.AddPANDocResponseDTO;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.GDCVerificationResponseDTO;
import in.ongrid.b2cverification.model.dto.response.PANVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.BaseVerification;
import in.ongrid.b2cverification.service.OnGridAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class OnGridAPIServiceImpl implements OnGridAPIService {

    @Value("${ongrid.api.base.url}")
    private String url;

    @Value("${ongrid.gdcverification.request.base.url}")
    private String gdcUrl;

    @Value("${ongrid.gdcverification.result.base.url}")
    private String gdcResultUrl;

    @Value("${ongrid.addpan.url}")
    private String addPanUrl;

    @Value("${ongrid.panverification.request.base.url}")
    private String panVerificationUrl;

    @Value("${ongrid.panverification.result.base.url}")
    private String panVerificationResultUrl;

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

    @Override
    public AddPANDocResponseDTO addPANDocument(String individualId, PANDocDTO panDocDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        // No need to set headers content-type at the main level, it will be set per field

        // Create multipart body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // Force text-based multipart fields
        HttpHeaders textHeaders = new HttpHeaders();
        textHeaders.setContentType(MediaType.TEXT_PLAIN);

        body.add("nameAsPerDocument", new HttpEntity<>(panDocDTO.getNameAsPerDocument(), textHeaders));
        body.add("documentUID", new HttpEntity<>(panDocDTO.getDocumentUID(), textHeaders));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String url = String.format(addPanUrl, individualId);

        ResponseEntity<AddPANDocResponseDTO> response = restTemplate.postForEntity(url, requestEntity, AddPANDocResponseDTO.class);

        return response.getBody();
    }



    @Override
    public PANVerificationResponseDTO postPANVerification(String documentId, long ogIndividualId) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBasicAuth(username, password);
//
//        PANDocDTO requestBody = new PANDocDTO();
//        requestBody.setDocumentUID(documentId);
//
//
//        HttpEntity<PANDocDTO> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        String url = String.format(panVerificationUrl, ogIndividualId);
//        System.out.println("POST URL: " + url);
//
//
//        ResponseEntity<PANVerificationResponseDTO> response = restTemplate.postForEntity(url, requestEntity, PANVerificationResponseDTO.class);
//
//        return response.getBody();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        PANBodyDTO requestBody = new PANBodyDTO();
        System.out.println("................................................................");
        int finalId = Integer.parseInt(documentId);
        System.out.println("This is finalid " + finalId);
        requestBody.setDocumentId(finalId);

        HttpEntity<PANBodyDTO> requestEntity = new HttpEntity<>(requestBody, headers);

        String url = String.format(panVerificationUrl, ogIndividualId);
        ResponseEntity<PANVerificationResponseDTO> response = restTemplate.postForEntity(url, requestEntity, PANVerificationResponseDTO.class);
        return response.getBody();
    }




}