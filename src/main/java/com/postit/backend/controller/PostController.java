package com.postit.backend.controller;

import com.postit.backend.models.Post;
import com.postit.backend.models.PostDto;
import com.postit.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;
    @GetMapping
    public Page<PostDto> list(Pageable pageable){
        return postService.getAllPosts(pageable);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto post, @RequestHeader("user") String user,@RequestHeader("token") String token)
    {
        return new ResponseEntity<>(postService.createPost(post,user,token), HttpStatus.CREATED);
    }
}
