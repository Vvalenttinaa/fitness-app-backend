package com.example.fitnessapp.models.requests;

import com.example.fitnessapp.models.dtos.Attribute;
import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private String category;
  //  private List<AttributeDescriptionRequest> atributes;
    private String search;
    private String attribute;
    private String description;
}
