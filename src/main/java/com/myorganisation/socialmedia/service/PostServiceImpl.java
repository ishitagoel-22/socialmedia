package com.myorganisation.socialmedia.service;

import com.myorganisation.socialmedia.dto.request.CommentRequestDto;
import com.myorganisation.socialmedia.dto.request.PostRequestDto;
import com.myorganisation.socialmedia.dto.response.CommentResponseDto;
import com.myorganisation.socialmedia.dto.response.GenericResponseDto;
import com.myorganisation.socialmedia.dto.response.PostResponseDto;
import com.myorganisation.socialmedia.entity.Comment;
import com.myorganisation.socialmedia.entity.Post;
import com.myorganisation.socialmedia.repository.CommentRepository;
import com.myorganisation.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl  implements PostService {

    @Autowired
    private RedisService redisService;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public GenericResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setContent(postRequestDto.getContent());
        post.setAuthorId(postRequestDto.getAuthorId());
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        GenericResponseDto response = new GenericResponseDto();
        response.setIsSuccess(true);
        response.setMessage("Post created successfully");
        response.setDetails(savedPost.getId());
        return response;
    }

    @Override
    public GenericResponseDto likePost(Long postId) {

        GenericResponseDto response = new GenericResponseDto();

        if (!postRepository.existsById(postId)) {
            response.setIsSuccess(false);
            response.setMessage("Post not found");
            return response;
        }

        String key = "post:" + postId + ":virality_score";
        System.out.println("Redis increment called for postId: " + postId);
        redisService.increment(key, 20);

        response.setIsSuccess(true);
        response.setMessage("Post liked successfully");
        return response;
    }

    @Override
    public GenericResponseDto addComment(Long postId, CommentRequestDto dto) {

        GenericResponseDto response = new GenericResponseDto();

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            response.setIsSuccess(false);
            response.setMessage("Post not found");
            return response;
        }

        int depth = 0;

        if (dto.getParentCommentId() != null) {
            Comment parent = commentRepository.findById(dto.getParentCommentId()).orElse(null);

            if (parent == null) {
                response.setIsSuccess(false);
                response.setMessage("Parent comment not found");
                return response;
            }

            depth = parent.getDepthLevel() + 1;

            if (depth > 20) {
                response.setIsSuccess(false);
                response.setMessage("Depth limit exceeded");
                return response;
            }
        }

        String botKey = "post:" + postId + ":bot_count";
        Long count = redisService.increment(botKey, 1);

        if (count > 100) {
            response.setIsSuccess(false);
            response.setMessage("Too many bot replies");
            return response;
        }

        String cooldownKey = "cooldown:" + dto.getAuthorId();

        if (redisService.exists(cooldownKey)) {
            response.setIsSuccess(false);
            response.setMessage("Cooldown active");
            return response;
        }

        redisService.setWithExpiry(cooldownKey, "1", 10);

        redisService.increment("post:" + postId + ":virality_score", 50);

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAuthorId(dto.getAuthorId());
        comment.setContent(dto.getContent());
        comment.setDepthLevel(depth);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        response.setIsSuccess(true);
        response.setMessage("Comment added successfully");

        return response;
    }


}
