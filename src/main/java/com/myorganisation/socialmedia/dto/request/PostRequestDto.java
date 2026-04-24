package com.myorganisation.socialmedia.dto.request;

import lombok.Data;

@Data
public class PostRequestDto {
    private String content;
    private Long authorId;
}
