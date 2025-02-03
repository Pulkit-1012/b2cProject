package in.ongrid.b2cverification.service.impl;


import in.ongrid.b2cverification.config.JwtService;
import in.ongrid.b2cverification.dao.UserRepository;
import in.ongrid.b2cverification.model.dto.AuthenticationRequest;
import in.ongrid.b2cverification.model.dto.RegisterRequest;
import in.ongrid.b2cverification.model.dto.response.AuthenticationResponse;
import in.ongrid.b2cverification.model.entities.BaseEntity;
import in.ongrid.b2cverification.model.entities.User;
import in.ongrid.b2cverification.model.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .userName(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(UserType.USER)
                .build();
        userRepository.save(user);
        var jwtToken = "Bearer "+jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        var jwtToken = "Bearer "+jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
