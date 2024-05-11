package com.example.fitnessapp.models.requests;

import lombok.Data;

@Data
public class CommentRequest {
    String content;
    Integer userId;
    Integer programId;
}
