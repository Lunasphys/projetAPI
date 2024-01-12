package org.example.controller;

import org.example.model.Post;
import org.example.model.User;
import org.example.service.JwtService;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final JwtService jwtService;

    public PostController(PostService postService, JwtService jwtService) {
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        User user = jwtService.validateToken(token);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Post> posts;
        if (user.isAdmin()) {
            posts = (List<Post>) postService.getAllPosts();
        } else {
            posts = postService.getPostsByAuthor(user.getId());
        }

        return ResponseEntity.ok(posts);
    }
}
