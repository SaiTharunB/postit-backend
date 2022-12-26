package com.postit.backend.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class User {
    @NotBlank(message = "id should not be blank/null")
    private String id;
    @NotBlank(message = "name should not be blank/null")
    private String name;
    @Email(message = "email is not valid")
    private String email;
    @Size(min = 10,max = 10,message = "contact number should be of 10 digits")
    private String contact;
}
