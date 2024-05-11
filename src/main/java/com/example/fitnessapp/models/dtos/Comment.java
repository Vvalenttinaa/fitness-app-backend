package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.ReplyEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class Comment {
    private Integer id;
    private String content;
    private String userUsername;
    private Integer programId;
    private List<Reply> replies;
}
