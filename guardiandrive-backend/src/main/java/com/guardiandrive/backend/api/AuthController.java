package com.guardiandrive.backend.api;

import com.guardiandrive.backend.dto.AuthenticationRequest;
import com.guardiandrive.backend.dto.AuthenticationResponse;
import com.guardiandrive.backend.dto.GoogleTokenRequest;
import com.guardiandrive.backend.dto.RegisterRequest;
import com.guardiandrive.backend.service.AuthenticationService;
import com.guardiandrive.backend.service.OAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private final OAuthService oAuthService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/google")
    public ResponseEntity<AuthenticationResponse> googleAuth(
            @Valid @RequestBody GoogleTokenRequest request
    ) {
        return ResponseEntity.ok(oAuthService.authenticateWithGoogle(request.getIdToken()));
    }
}
