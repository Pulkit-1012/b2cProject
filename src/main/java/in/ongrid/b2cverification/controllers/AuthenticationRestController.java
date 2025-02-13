package in.ongrid.b2cverification.controllers;


import in.ongrid.b2cverification.model.dto.AuthenticationRequest;
import in.ongrid.b2cverification.model.dto.RegisterRequest;
import in.ongrid.b2cverification.model.dto.response.AuthenticationResponse;
import in.ongrid.b2cverification.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
//    @CrossOrigin(origins = "http://localhost:5173/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
