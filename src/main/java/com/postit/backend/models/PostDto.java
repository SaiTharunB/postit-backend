package com.postit.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Document(collection = "posts")
public class PostDto {
    @Id
    private String id;
    @NotBlank(message = "title should not be blank/null")
    private String title;
    @NotBlank(message = "content should not br blank/null")
    private String body;
    @NotBlank(message="author should not be blank/null")
    private String author;
    private LocalDateTime created;
    private int likes;
}
