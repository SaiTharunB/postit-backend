package com.postit.backend.repository;

import com.postit.backend.models.DbUser;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<DbUser,String> {
    DbUser findByNameAndPassword(String name,String password);
    DbUser findByNameAndToken(String name,String token);

    DbUser findByNameIgnoreCase(String name);
}
