package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.AttributeEntity;
import com.example.fitnessapp.models.entities.ProgramEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class AttributeDescription {
    private String description;
    private Integer attributeId;
    private String attributeName;
  //  private Integer programId;
    private Integer id;
  //  private Attribute attributeByAttributeId;
  //  private Program programByProgramId;
}
