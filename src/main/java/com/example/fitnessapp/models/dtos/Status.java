package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.PaymentMethodEntity;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
public class Status {
    private Date createdAt;
    private Integer programId;
    private Integer userId;
    private Integer paymentMethodId;
    private String paymentMethodName;
    private Byte paid;
    private Integer id;
    private Program programByProgramId;
    private User userByUserId;
    private PaymentMethod paymentMethodByPaymentMethodId;
    private String state;
}
