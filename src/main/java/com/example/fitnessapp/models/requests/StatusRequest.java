package com.example.fitnessapp.models.requests;

import com.example.fitnessapp.models.entities.PaymentMethodEntity;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class StatusRequest {
    @NotNull
    private Integer programId;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer paymentMethodId;
    @NotNull
    private Boolean paid;
}
