package com.example.fitnessapp.models.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MessagePK {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
}
