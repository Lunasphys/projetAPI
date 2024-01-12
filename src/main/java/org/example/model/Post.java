package org.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Post {
    private final int id;
    private final int authorId;
    private final String content;

    public Post() {
        this.id = 0;
        this.authorId = 0;
        this.content = "";
    }

    public Post(int id, int authorId, String content) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public int getId() {
        return this.id;
    }

    public int getIdAuthor() {
        return this.authorId;
    }

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


}
