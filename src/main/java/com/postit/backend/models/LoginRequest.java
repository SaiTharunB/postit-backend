package com.postit.backend.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "username should not be null")
    private String username;
    @Size(min = 5,message = "password should be minimum 5 characters")
    private String password;
}
