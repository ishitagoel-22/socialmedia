package com.myorganisation.socialmedia.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long postId;
    private Long authorId;
    private LocalDateTime createdDate;
    private int depthLevel;

}
