package com.postit.backend.service;

import com.postit.backend.exception.CustomException;
import com.postit.backend.models.Comment;
import com.postit.backend.models.Post;
import com.postit.backend.models.PostDto;
import com.postit.backend.repository.CommentRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends BaseService{
    @Autowired
    CommentRepository commentRepository;
    private static Logger logger = LoggerFactory.getLogger(PostService.class);
    public Comment createComment(Comment comment, String user, String token)
    {
        logger.info("authenticating user: {}",user);
        validationService.authenticateUser(user,token);
        logger.info("creating comment: {}",comment);
        comment.setCreated(LocalDateTime.now());
        Comment resp=commentRepository.save(comment);
        if(resp==null)
        {
            throw new CustomException("failed to comment", HttpStatus.BAD_REQUEST);
        }
        return resp;
    }
    public void deleteComment(String commentId,String user,String token)
    {
        logger.info("deleting comment with id :: {}",commentId);
        validationService.validateId(commentId);
        Optional<Comment> comment=commentRepository.findById(commentId);
        if(!comment.isPresent())
        {
            throw new CustomException("failed : comment not found",HttpStatus.BAD_REQUEST);
        }
        validationService.checkUpdateDeleteAuth(user,token,comment.get().getAuthor());
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getCommentsbyPostId(String postId, String user, String token) {
        logger.info("fetching comments for post with id :: {}",postId);
        validationService.validateId(postId);
        validationService.authenticateUser(user,token);
        return commentRepository.findByPostId(postId);
    }
}
