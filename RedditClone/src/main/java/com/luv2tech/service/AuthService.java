package com.luv2tech.service;

import com.luv2tech.payload.LoginPayload;
import com.luv2tech.payload.RegistrationPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface AuthService {

    ResponseEntity<?> signUp(RegistrationPayload payload, BindingResult result);

    ResponseEntity<?> verifyAccount(String token);

    ResponseEntity<?> login(LoginPayload payload, BindingResult result);


}
