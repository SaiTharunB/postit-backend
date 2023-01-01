package com.postit.backend.service;


import com.postit.backend.exception.CustomException;
import com.postit.backend.models.DbUser;
import com.postit.backend.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.xml.bind.ValidationException;
import java.util.Map;
import java.util.Optional;


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
    public void authenticateUser(String username,String token)
    {
        logger.info("authenticating user :: {}",username);
        DbUser user =userRepository.findByNameAndToken(username,token);
        if(user==null)
        {
            throw new CustomException("failed to authenticate", HttpStatus.UNAUTHORIZED);
        }
    }

    public void checkUpdateDeleteAuth(String username,String token,String creator)
    {
        authenticateUser(username,token);
        if(!(StringUtils.equalsIgnoreCase(username,creator) || StringUtils.equalsIgnoreCase("ADMIN",username))){
            throw new CustomException("you are not authorized to perform this operation",HttpStatus.UNAUTHORIZED);
        }
    }
    public void validateId(String id)
    {
        if(StringUtils.isBlank(id))
        {
            throw new CustomException("id is invalid",HttpStatus.BAD_REQUEST);
        }
    }
    public Pageable validatePageRequest(Pageable pageable) {
        Optional<Sort.Order> sortOrder = pageable.getSort().stream().findFirst();
        if (!sortOrder.isPresent()) {
            return PageRequest.of(0, 10, Sort.by("created").descending());
        }
        return pageable;
    }
    public void validateLikesRequest(Map<String,String> request)
    {
        if(!request.containsKey("id"))
        {
            throw new CustomException("id doesn't exist",HttpStatus.BAD_REQUEST);
        }
        if(!request.containsKey("operation"))
        {
            throw new CustomException("operation doesn't exist",HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(request.get("id"))){
            throw new CustomException("id should not be blank or null",HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.equalsAnyIgnoreCase(request.get("operation"),"like","unlike"))
        {
            throw new CustomException("operation should be like or unlike",HttpStatus.BAD_REQUEST);
        }
    }
}
