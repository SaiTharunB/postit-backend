package com.postit.backend.service;

import com.postit.backend.models.Like;
import com.postit.backend.repository.LikeRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends BaseService{

    private static Logger logger = LoggerFactory.getLogger(LikeService.class);

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;
    public void saveLike(String id,String type, String user,String token)
    {
        Like like=new Like(user,type,id);
        logger.info("saving like :: {}",like);
        validationService.authenticateUser(user,token);
        Like dbLike=likeRepository.findByUsernameAndEntityIdAndType(like.getUsername(), like.getEntityId(), like.getType());
        if(dbLike!=null)
        {
            logger.info("disLike saved");
            likeRepository.deleteById(dbLike.getId());
            if(StringUtils.equalsIgnoreCase(type,"post"))
            {
                postService.updateLikes(id,"DEC");
            }
            else{
                commentService.updateLikes(id,"DEC");
            }
        }
        else {
            logger.info("like saved");
            likeRepository.save(like);
            if(StringUtils.equalsIgnoreCase(type,"post"))
            {
                postService.updateLikes(id,"INC");
            }
            else{
                commentService.updateLikes(id,"INC");
            }
        }
    }
}
