package com.postit.backend.repository;

import com.postit.backend.models.Post;
import com.postit.backend.models.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<PostDto,String> {
    @Aggregation({"{$lookup: { from: 'comments', localField: '_id', foreignField: 'postId', as: 'comments' }}" })
    List<Post> getAllPostsCount();

    @Aggregation({"{$lookup: { from: 'comments', localField: '_id', foreignField: 'postId', as: 'comments' }}" })
    List<Post> getAllPosts(Pageable pageable);

}
