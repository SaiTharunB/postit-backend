package com.postit.backend.service;

import com.postit.backend.models.Like;
import com.postit.backend.repository.LikeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends BaseService{

    private static Logger logger = LoggerFactory.getLogger(LikeService.class);

    @Autowired
    LikeRepository likeRepository;
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
        }
        else {
            logger.info("like saved");
            likeRepository.save(like);
        }
    }
}
