package com.example.fitnessapp.models.requests;

import com.example.fitnessapp.models.dtos.Attribute;
import com.example.fitnessapp.models.dtos.Category;
import com.example.fitnessapp.models.dtos.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ProgramRequest {
    @NotBlank
    private String name;
    @NotEmpty
    private List<ImageRequest> images;
    @NotBlank
    private Double price;
    @NotBlank
    private String description;
    @NotBlank
    private Integer level;
    @NotBlank
    private Integer duration;
    @NotBlank
    private String contact;
    @NotBlank
    private String locationName;
    @NotBlank
    private String instructorName;
    @NotBlank
    private String categoryName;
    @NotEmpty
    private List<AttributeDescriptionRequest> attributes;
}
