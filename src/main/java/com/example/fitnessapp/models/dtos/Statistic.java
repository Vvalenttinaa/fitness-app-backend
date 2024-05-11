package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.DiaryEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.sql.Date;
@Data
public class Statistic {
    private Integer id;
    private Date date;
    private String description;
    private Integer duration;
    private Integer intensity;
    private Integer result;
    private Double weight;
    private Integer diaryId;
}
