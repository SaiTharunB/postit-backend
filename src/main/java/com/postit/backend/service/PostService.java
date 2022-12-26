package com.postit.backend.service;

import com.postit.backend.exception.CustomException;
import com.postit.backend.models.Post;
import com.postit.backend.models.PostDto;
import com.postit.backend.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostService extends BaseService{
    private static Logger logger = LoggerFactory.getLogger(PostService.class);
    @Autowired
    PostRepository postRepository;
    public Post createPost(PostDto postDto,String user,String token)
    {
        logger.info("authenticating user: {}",user);
        validationService.authenticateUser(user,token);
        logger.info("creating post: {}",postDto);
        postDto.setCreated(LocalDateTime.now());
        PostDto resp=postRepository.save(postDto);
        if(resp==null)
        {
            throw new CustomException("failed to create post", HttpStatus.BAD_REQUEST);
        }
        return modelMapper.map(resp,Post.class);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        logger.info("fetching posts");
        pageable=validationService.validatePageRequest(pageable);
        long totalCount = postRepository.getAllPostsCount().size();
        if(totalCount>0) {
            logger.info("total posts found :: {}",totalCount);
            List<Post> posts = postRepository.getAllPosts(pageable);
            return new PageImpl<>(posts, pageable, totalCount);
        }
        return new PageImpl<>(Collections.emptyList(), pageable, totalCount);
    }
}
