package com.luv2tech.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginPayload {
    @NotBlank(message = "Username is required!")
    private String username;
    @NotBlank(message = "Password is required!")
    private String password;
}
