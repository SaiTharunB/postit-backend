package com.postit.backend.controller;

import com.postit.backend.models.Comment;
import com.postit.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/{id}")
    public List<Comment> getCommentsByPost(@PathVariable("id") String postId, @RequestHeader("username") String user,@RequestHeader("token") String token)
    {
        return commentService.getCommentsbyPostId(postId,user,token);
    }
    @PostMapping
    public Comment createComment(@Valid @RequestBody Comment comment, @RequestHeader("username") String user,@RequestHeader("token") String token)
    {
        return commentService.createComment(comment,user,token);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") String id, @RequestHeader("username") String user,@RequestHeader("token") String token)
    {
        commentService.deleteComment(id,user,token);
        return ResponseEntity.ok().build();
    }
}
