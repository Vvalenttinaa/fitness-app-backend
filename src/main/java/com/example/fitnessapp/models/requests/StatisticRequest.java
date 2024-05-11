package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StatisticRequest {

    @Size(max = 255)
    private String description;
    private Integer duration;
    private Integer intensity;
    @NotNull
    private Integer result;
    @NotNull
    private Double weight;
}
