package com.postit.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Data
@Document(collection = "comments")
public class CommentDto {
    @Id
    private String id;
    private String comment;
    private String author;
    @Field(targetType = FieldType.OBJECT_ID)
    private String postId;
    private int likes;

    private LocalDateTime created;
}
