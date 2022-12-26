package com.postit.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;


@Data
@Document(collection = "users")
public class DbUser {
    @Id
    private String id;
    @NotBlank(message = "name should not be null/blank")
    private String name;

    @Size(min = 5,message = "password should be minimum 5 characters")
    private String password;
    @Email(message = "email is not valid")
    private String email;

    @Size(min = 10,max = 10,message = "contact number should be of 10 digits")
    private String contact;

    private String token;
}
