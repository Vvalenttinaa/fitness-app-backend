package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class MessageRequest {
    @NotEmpty
    private String content;
    @NotNull
    private Integer senderId;
    @NotNull
    private Integer receiverId;
}
