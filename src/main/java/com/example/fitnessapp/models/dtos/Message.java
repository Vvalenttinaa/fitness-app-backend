package com.example.fitnessapp.models.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Message {
    private Integer id;
    private String content;
    private Timestamp dateAndTime;
    private User sender;
    private User receiver;
}
