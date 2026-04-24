package com.myorganisation.socialmedia.service;

import com.myorganisation.socialmedia.dto.request.CommentRequestDto;
import com.myorganisation.socialmedia.dto.request.PostRequestDto;
import com.myorganisation.socialmedia.dto.response.CommentResponseDto;
import com.myorganisation.socialmedia.dto.response.GenericResponseDto;
import com.myorganisation.socialmedia.dto.response.PostResponseDto;
import com.myorganisation.socialmedia.entity.Post;

public interface PostService {
    GenericResponseDto createPost(PostRequestDto postRequestDto);
    GenericResponseDto likePost(Long postId);
    GenericResponseDto addComment(Long postId, CommentRequestDto request);
}
