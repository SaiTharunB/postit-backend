package com.postit.backend.service;


import com.postit.backend.exception.CustomException;
import com.postit.backend.models.DbUser;
import com.postit.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
public class ValidationService {
    private static Logger logger = LoggerFactory.getLogger(ValidationService.class);
    @Autowired
    UserRepository userRepository;

    public void checkUserNameisUnique(String name)
    {
        DbUser user =userRepository.findByNameIgnoreCase(name);
        if(user!=null)
        {
            throw new CustomException("username already exists", HttpStatus.BAD_REQUEST);
        }
    }
}
