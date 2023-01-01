package com.postit.backend.controller;

import com.postit.backend.models.Like;
import com.postit.backend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    LikeService likeService;
    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> like(@PathVariable("id")String id,@RequestParam("type")String type, @RequestHeader("user") String user, @RequestHeader("token") String token){
        likeService.saveLike(id,type,user,token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
