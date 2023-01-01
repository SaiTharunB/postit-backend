package com.postit.backend.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Post extends PostDto{
    private int noOfComments;
    private boolean isLiked=false;
}
