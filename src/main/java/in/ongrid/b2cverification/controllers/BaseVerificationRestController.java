package in.ongrid.b2cverification.controllers;

import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.DocumentRepository;
import in.ongrid.b2cverification.dao.IndividualRepository;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.model.dto.response.BaseVerificationResponseDTO;
import in.ongrid.b2cverification.model.entities.*;
import in.ongrid.b2cverification.model.enums.DocType;
import in.ongrid.b2cverification.model.enums.OfferingType;
import in.ongrid.b2cverification.model.enums.State;
import in.ongrid.b2cverification.service.BaseVerificationService;
import in.ongrid.b2cverification.service.PANDocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class BaseVerificationRestController {

    private final BaseVerificationService baseVerificationService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final IndividualRepository individualRepository;
    private final DocumentRepository documentRepository;
    private final PANDocService panDocService;

    public BaseVerificationRestController(BaseVerificationService baseVerificationService, JwtService jwtService, UserRepository userRepository, IndividualRepository individualRepository, DocumentRepository documentRepository, PANDocService panDocService) {
        this.baseVerificationService = baseVerificationService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.individualRepository = individualRepository;
        this.documentRepository = documentRepository;
        this.panDocService = panDocService;
    }






    @PostMapping("/{userId}/individuals/{individualId}/verify-gdc")
    public ResponseEntity<BaseVerificationResponseDTO> initiateGDCVerification(@PathVariable long userId,
                                                                               @PathVariable long individualId,
                                                                               @RequestHeader("Authorization") String token) {
        BaseVerificationResponseDTO baseVerificationResponseDTO = baseVerificationService.requestGDCverification(userId, individualId, token);
        return ResponseEntity.ok(baseVerificationResponseDTO);
    }

    @GetMapping("/{userId}/individuals/{individualId}/verify-gdc/{requestId}")
    public ResponseEntity<BaseVerificationResponseDTO> checkGDCVerificationStatus(@PathVariable long userId,
                                                                                  @PathVariable long individualId,
                                                                                  @PathVariable long requestId,
                                                                                  @RequestHeader("Authorization") String token) {
        BaseVerificationResponseDTO baseVerificationResponseDTO = baseVerificationService.checkGDCVerificationStatus(userId, individualId, token, requestId);
        return ResponseEntity.ok(baseVerificationResponseDTO);
    }

}
