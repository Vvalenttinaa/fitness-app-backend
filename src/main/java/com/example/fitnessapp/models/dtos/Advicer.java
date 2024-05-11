package com.example.fitnessapp.models.dtos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Advicer {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
