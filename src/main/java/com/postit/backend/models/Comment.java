package com.postit.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment extends CommentDto{
    private boolean isLiked;
}
