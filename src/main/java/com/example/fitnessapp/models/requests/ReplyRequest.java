package com.example.fitnessapp.models.requests;

import lombok.Data;

@Data
public class ReplyRequest {
    String content;
    Integer commentId;
    Integer userId;
}
