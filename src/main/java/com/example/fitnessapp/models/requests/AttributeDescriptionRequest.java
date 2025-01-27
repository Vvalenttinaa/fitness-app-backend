package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttributeDescriptionRequest {
    @NotBlank
    private String name;
    @NotBlank
    private Integer attributeId;
}
