package com.postit.backend.controller;

import com.postit.backend.models.DbUser;
import com.postit.backend.models.LoginRequest;
import com.postit.backend.models.User;
import com.postit.backend.service.UserService;
import com.postit.backend.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody DbUser user)
    {
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @RequestHeader("user") String username,@RequestHeader("token") String token)
    {
        return new ResponseEntity<>(userService.updateUser(user,username,token), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        return new ResponseEntity<>(userService.loginUser(loginRequest),HttpStatus.OK);
    }
}
