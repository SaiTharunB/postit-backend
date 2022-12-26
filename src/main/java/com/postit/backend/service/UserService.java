package com.postit.backend.service;

import com.postit.backend.exception.CustomException;
import com.postit.backend.models.DbUser;
import com.postit.backend.models.LoginRequest;
import com.postit.backend.models.User;
import com.postit.backend.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
public class UserService extends BaseService{
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
    public User createUser(DbUser user)
    {
        logger.info("creating user {}",user);
        user.setId(null);
        validationService.checkUserNameisUnique(user.getName());
        user.setToken(getHash(user.getPassword()));
        user= userRepository.save(user);
        if(user==null)
        {
            throw new CustomException("Failed to create user",HttpStatus.BAD_REQUEST);
        }
        return modelMapper.map(user, User.class);
    }

    public String loginUser(LoginRequest loginRequest) {
        logger.info("login request received");
        DbUser user =userRepository.findByNameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());
        if(user==null)
        {
            throw new CustomException("user with the credentials does not exist", HttpStatus.NOT_FOUND);
        }
        return user.getToken();
    }
    public void authenticateUser(String username,String token)
    {
        logger.info("authenticating user with name: {}, token: {}",username,token);
        DbUser user =userRepository.findByNameAndPassword(username,token);
        if(user==null)
        {
            throw new CustomException("user with the credentials does not exist", HttpStatus.NOT_FOUND);
        }
    }

    public User updateUser(User user) {
        logger.info("in update user");
        Optional<DbUser> dbUser = userRepository.findById(user.getId());
        if(!dbUser.isPresent()){
            throw new CustomException("user not found",HttpStatus.NOT_FOUND);
        }
        if(!StringUtils.equalsIgnoreCase(user.getName(),dbUser.get().getName()))
        {
            validationService.checkUserNameisUnique(user.getName());
        }
        DbUser resp= userRepository.save(mergeFields(user,dbUser.get()));
        if(resp==null)
        {
            throw new CustomException("update failed",HttpStatus.EXPECTATION_FAILED);
        }
        return modelMapper.map(resp,User.class);
    }
    private DbUser mergeFields(User user,DbUser dbUser)
    {
        dbUser.setName(user.getName());
        dbUser.setEmail(user.getEmail());
        dbUser.setContact(user.getContact());
        dbUser.setPassword(dbUser.getPassword());
        return dbUser;
    }
    private String getHash(String password)
    {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
