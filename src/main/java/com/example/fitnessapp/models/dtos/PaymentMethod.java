package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.StatusEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.util.List;

public class PaymentMethod {
    private Integer id;
    private String name;
  //  private List<Status> statusesById;
}
