package com.postit.backend.repository;

import com.postit.backend.models.CommentDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentDto,String> {
    List<CommentDto> findByPostId(String postId);

    List<CommentDto> findByPostIdIn(List<String> ids);
}
