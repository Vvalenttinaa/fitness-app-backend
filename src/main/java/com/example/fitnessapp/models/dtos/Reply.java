package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.CommentEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class Reply {
    private Integer id;
    private String content;
    private Integer commentId;
    private String userUsername;
    private Integer userId;
}
