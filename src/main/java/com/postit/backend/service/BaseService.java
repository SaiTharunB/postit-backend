package com.postit.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    @Autowired
    ValidationService validationService;
    ModelMapper modelMapper= new ModelMapper();
}
