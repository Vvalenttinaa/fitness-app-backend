package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.AttributeEntity;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.SubscriptionEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class Category {
    private Integer id;
    private String name;
    private List<Attribute> attributes;
    //private List<Program> programsById;
  //  private List<Subscription> subscriptionsById;
}
