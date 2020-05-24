package com.luv2tech.controller;

import com.luv2tech.payload.RegistrationPayload;
import com.luv2tech.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody RegistrationPayload payload,
                                    BindingResult result) {
        return authService.signUp(payload, result);
    }

    @GetMapping("account-verification/{token}")
    public ResponseEntity<?>  verifyAccount(@PathVariable("token") String token){
        return authService.verifyAccount(token);
    }
}
