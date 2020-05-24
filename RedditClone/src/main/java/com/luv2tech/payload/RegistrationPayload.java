package com.luv2tech.payload;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RegistrationPayload {

    @NotBlank(message = "Email is required!")
    @NotNull(message = "Email is required!")
    @Email(message = "Provide valid email id")
    private String email;
    @NotBlank(message = "User name is required!")
    @NotNull(message = "User name is required!")
    private String username;
    @NotBlank(message = "Password is required!")
    @NotNull(message = "Password is required!")
    private String password;
    @NotBlank(message = "Name is required!")
    @NotNull(message = "Name is required!")
    private String name;
}
