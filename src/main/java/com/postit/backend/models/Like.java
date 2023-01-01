package com.postit.backend.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "likes")
@Data
public class Like {
    private String id;
    private String username;
    private String type;

    @Field(targetType = FieldType.OBJECT_ID)
    private String entityId;

    public Like(String username,String type,String entityId)
    {
        this.username=username;
        this.type=type;
        this.entityId=entityId;
    }
}
