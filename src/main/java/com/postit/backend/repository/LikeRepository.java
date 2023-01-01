package com.postit.backend.repository;

import com.postit.backend.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRepository extends MongoRepository<Like,String> {
    Like findByUsernameAndEntityIdAndType(String name,String entityId,String type);

    List<Like> findByUsernameAndType(String name, String type);
}
