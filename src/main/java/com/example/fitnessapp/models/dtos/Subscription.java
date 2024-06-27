package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.CategoryEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class Subscription {
    private Date date;
    private Integer userId;
    private Integer categoryId;
    private String categoryName;
    private Integer id;
}
