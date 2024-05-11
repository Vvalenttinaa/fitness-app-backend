package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.AttributedescriptionEntity;
import com.example.fitnessapp.models.entities.CategoryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class Attribute {
    private Integer id;
    private String name;
    private Integer categoryId;
 //   private Category categoryByCategoryId;
    private List<AttributeDescription> attributedescriptionsById;

}
