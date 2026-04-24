package com.myorganisation.socialmedia.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    private Long id;
    private String content;
    private Long authorId;
    private LocalDateTime createdAt;
}
