package com.postit.backend.service;

import com.postit.backend.exception.CustomException;
import com.postit.backend.models.CommentDto;
import com.postit.backend.models.Post;
import com.postit.backend.models.PostDto;
import com.postit.backend.repository.CommentRepository;
import com.postit.backend.repository.PostRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostService extends BaseService{
    private static Logger logger = LoggerFactory.getLogger(PostService.class);
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MongoTemplate mongoTemplate;
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
        return modelMapper.map(resp, Post.class);
    }

    public Page<Post> getAllPosts(Pageable pageable,String user,String token) {
        logger.info("fetching posts");
        validationService.authenticateUser(user,token);
        pageable=validationService.validatePageRequest(pageable);
        long totalCount = postRepository.findAll().size();
        if(totalCount>0) {
            logger.info("total posts found :: {}",totalCount);
           List<PostDto> posts = postRepository.findAll(pageable).getContent();
            return new PageImpl<>(populateComments(posts,user), pageable, totalCount);
        }
        return new PageImpl<>(Collections.emptyList(), pageable, totalCount);
    }

    private List<Post> populateComments(List<PostDto> postDtos,String user)
    {
        Map<String,List<CommentDto>> map =commentRepository.findByPostIdIn(postDtos.parallelStream().map(p->p.getId()).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.groupingBy(CommentDto::getPostId));
       HashSet<String> set= (HashSet<String>) likeRepository.findByUsernameAndType(user,"post")
                .stream()
               .map(dto->dto.getEntityId())
                .collect(Collectors.toSet());
        return postDtos.stream().map(dto->{
            Post post = modelMapper.map(dto, Post.class);
            if(map.containsKey(post.getId())){
                post.setNoOfComments(map.get(post.getId()).size());
            }
            if(set.contains(post.getId()))
            {
                post.setLiked(true);
            }
            return post;
        }).collect(Collectors.toList());
    }
    public PostDto updateLikes(String postId,String opType)
    {
        logger.info("Updating likes operation :: {}",opType);
        Optional<PostDto> resp=postRepository.findById(postId);
        if(resp.isPresent()) {
            PostDto postDto = resp.get();
            if (StringUtils.equalsIgnoreCase(opType, "INC")) {
                postDto.setLikes(postDto.getLikes()+1);
            }
            else{
                postDto.setLikes(postDto.getLikes()-1);
            }
            return postRepository.save(postDto);
        }
        else{
            throw new CustomException("post not found",HttpStatus.BAD_REQUEST);
        }
    }

    public List<Post> getUserPosts(String user, String token) {
        logger.info("Fetching posts of :: {}",user);
        validationService.authenticateUser(user,token);
        List<PostDto> postDtos = postRepository.findByAuthor(user);
        if (!CollectionUtils.isEmpty(postDtos)){
            return populateComments(postDtos,user);
        }
        return Collections.emptyList();
    }
}
