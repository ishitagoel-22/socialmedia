package com.myorganisation.socialmedia.dto.response;

import lombok.Data;

@Data
public class GenericResponseDto {
    private Boolean isSuccess = false;
    private String message;
    private Object details;
}