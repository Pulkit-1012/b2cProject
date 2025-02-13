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





    //dont know if i have written rigth or not
//    @PostMapping("/{userId}/individuals/{individualId}/verify-pan")
//    public ResponseEntity<String> verifyPan(@PathVariable long userId, @PathVariable long individualId, @RequestHeader("Authorization") String token) {
//
//        String emailFromToken = jwtService.extractUsername(token.substring(7).trim());
//        Optional<User> dbUser = userRepository.findById(userId);
//
//        if(dbUser.isEmpty() || !dbUser.get().getEmail().equals(emailFromToken)) {
//            throw new RuntimeException("User doesn't exist OR Unauthorized access!");
//        }
//
//        Optional<Individual> individual = individualRepository.findById(individualId);
//
//        if(individual.isEmpty() || !individual.get().getAddedBy().equals(dbUser.get())) {
//            throw new RuntimeException("Individual doesn't exist OR The individual is not owned by this user!");
//        }
//
//
//        //finding PANDoc associated with this individual
//        Document document = documentRepository.findByIndividualIdAndDocType(individualId, DocType.PAN);
//        if(document == null) {
//            throw new RuntimeException("Document doesn't exist!");
//        }
//
//        if(!(document instanceof PANDoc)) {//checks if the document is a pandoc or not since pandoc extends document class and oandoc is s tyoe of document
//            throw new RuntimeException("Document is not PAN!");
//        }
//
//        PANDoc panDoc = (PANDoc) document;// down casting from superclass to subclass
//
//        User user = userRepository.findById(userId).get();//doubts if i should do this, becuase,
//        //in BaseVerification entity, we have mapped it with User entity using private User user
//        //so, it is 'RequestedByUserId' is expecting a user not the user's id.
//
//        BaseVerification baseVerification = new BaseVerification();
//        baseVerification.setRequestedByUserId(user);//I can pass user and not the user's id, look into the BaseVerification entiyt to understand the reason
//        baseVerification.setIndividual(individual.get());
//        baseVerification.setOfferingType(OfferingType.PAN);
//        baseVerification.setStatus(State.PENDING);//how to set this status to Complete once it is completed
//        //lots of doubts
//        //how to set result adn pdfreport
//
//        return ResponseEntity.ok("PAN Verification Successfully Initiated!");
//    }
}
