package com.myorganisation.socialmedia.dto.request;

import lombok.Data;

@Data
public class CommentRequestDto {

    private Long authorId;
    private String content;
    private Long parentCommentId;
}

