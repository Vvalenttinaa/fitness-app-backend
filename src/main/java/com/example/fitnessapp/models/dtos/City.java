package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.UserEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class City {
    private Integer id;
    private String name;
  //  private List<User> usersById;
}
