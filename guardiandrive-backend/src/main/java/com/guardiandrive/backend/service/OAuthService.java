package com.guardiandrive.backend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.guardiandrive.backend.dto.AuthenticationResponse;
import com.guardiandrive.backend.model.Role;
import com.guardiandrive.backend.model.User;
import com.guardiandrive.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Value("${google.client-id}")
    private String googleClientId;

    public AuthenticationResponse authenticateWithGoogle(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();

                User user = userRepository.findByEmail(email)
                        .orElseGet(() -> {
                            User newUser = User.builder()
                                    .email(email)
                                    // Generate a random password for OAuth users
                                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                                    .role(Role.USER)
                                    .build();
                            return userRepository.save(newUser);
                        });

                String jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            } else {
                throw new RuntimeException("Invalid Google ID Token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error verifying Google ID Token: " + e.getMessage());
        }
    }
}
