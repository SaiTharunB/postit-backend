package com.postit.backend.service;

import com.postit.backend.repository.LikeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    @Autowired
    ValidationService validationService;

    @Autowired
    LikeRepository likeRepository;
    ModelMapper modelMapper= new ModelMapper();
}
