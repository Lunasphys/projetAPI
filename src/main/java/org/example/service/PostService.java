package org.example.service;

import org.example.model.Post;
import org.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final Map<Integer, Post> posts = new HashMap<>();

    private final UserService userService;

    public PostService(UserService userService) {
        this.userService = userService;
    }

    public void loadPosts() throws IOException {
        File file = new java.io.File("./view/img/json/img.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Post[] postArray = objectMapper.readValue(file, Post[].class);
        for(Post post : postArray) {
            posts.put(post.getId(), post);
        }
    }

    public String getPostContent(int postId, String username){
        Post post = posts.get(postId);
        User user = userService.getUserByPseudo(username);

        if(post != null && user != null){

            if(user.getId() == post.getIdAuthor() || Objects.equals(user.getRole(), "ROLE_ADMIN")){
                return post.getContent();
            }
        }
        return "User does not have access to this post";
    }
    public List<Post> getPostsByAuthor(int idAuthor) {
        return posts.values().stream()
                .filter(post -> post.getIdAuthor() == idAuthor)
                .collect(Collectors.toList());
    }

    public Post createPost(Post post) {
        this.posts.put(post.getId(), post);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("./view/img/json/img.json"), this.posts.values());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return post;
    }

    public Collection<Post> getAllPosts() {
        return this.posts.values();
    }
}
