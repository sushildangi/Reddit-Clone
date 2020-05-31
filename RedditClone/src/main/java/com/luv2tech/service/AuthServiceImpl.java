package com.luv2tech.service;

import com.luv2tech.exceptions.SpringRedditException;
import com.luv2tech.model.User;
import com.luv2tech.model.VerificationToken;
import com.luv2tech.payload.LoginPayload;
import com.luv2tech.payload.RegistrationPayload;
import com.luv2tech.repository.UserRepository;
import com.luv2tech.repository.VerificationTokenRepository;
import com.luv2tech.response.ApiResponse;
import com.luv2tech.response.AuthenticationResponse;
import com.luv2tech.response.NotificationEmail;
import com.luv2tech.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ErrorCollector errorCollector;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<?> login(LoginPayload payload,
                                   BindingResult result) {
        ResponseEntity<?> responseEntity;
        if (result.hasErrors()) {
            responseEntity = new ResponseEntity<>(errorCollector.getErrorResponses(result), HttpStatus.BAD_REQUEST);
        } else {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            payload.getUsername(),
                            payload.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);
            responseEntity = new ResponseEntity<>(
                    new AuthenticationResponse(
                            token,
                            payload.getUsername()
                    ),
                    HttpStatus.OK
            );
        }
        return responseEntity;
    }

    @Override
    @Transactional
    public ResponseEntity<?> signUp(RegistrationPayload payload,
                                    BindingResult result) {
        ResponseEntity<?> responseEntity;
        if (result.hasErrors()) {
            responseEntity = new ResponseEntity<>(errorCollector.getErrorResponses(result), HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setUsername(payload.getUsername());
            user.setEmail(payload.getEmail());
            user.setPassword(passwordEncoder.encode(payload.getPassword()));
            user.setName(payload.getName());
            userRepository.saveAndFlush(user);
            String token = generateVerificationToken(user);
            mailService.sendMail(new NotificationEmail("Please Activate your Account",
                    user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:8080/api/auth/account-verification/" + token));
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{username}")
                    .buildAndExpand(user.getUsername()).toUri();

            responseEntity = ResponseEntity
                    .created(location)
                    .body(new ApiResponse(
                            true,
                            "User registered successfully"));
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<?> verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional =
                verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
        return new ResponseEntity<>(
                new ApiResponse(
                        true,
                        "Account Activate Successfully"
                ), HttpStatus.ACCEPTED
        );
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userRepository.saveAndFlush(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.saveAndFlush(verificationToken);
        return token;
    }
}
