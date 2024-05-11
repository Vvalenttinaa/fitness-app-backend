package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.StatisticEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class Diary {
    private Integer id;
  //  private List<Statistic> statisticsById;
  //  private List<User> usersById;

}
