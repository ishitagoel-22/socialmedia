package com.myorganisation.socialmedia.controller;

import com.myorganisation.socialmedia.dto.request.PostRequestDto;
import com.myorganisation.socialmedia.dto.request.CommentRequestDto;
import com.myorganisation.socialmedia.dto.response.GenericResponseDto;
import com.myorganisation.socialmedia.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<GenericResponseDto> createPost(@RequestBody PostRequestDto request) {
        return new ResponseEntity<>(postService.createPost(request), HttpStatusCode.valueOf(201));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<GenericResponseDto> addComment(@PathVariable Long postId, @RequestBody CommentRequestDto request) {
        return new ResponseEntity<>(postService.addComment(postId, request), HttpStatusCode.valueOf(201));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<GenericResponseDto> likePost(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.likePost(postId), HttpStatusCode.valueOf(200));
    }
}