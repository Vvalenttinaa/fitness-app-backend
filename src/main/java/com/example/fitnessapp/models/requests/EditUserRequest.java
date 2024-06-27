package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditUserRequest {
    @NotBlank
    @Size(min=8)
    private String password;
    @Email
    @Size(max=255)
    private String mail;
    @NotBlank
    @Size(max=255)
    private String firstName;
    @NotBlank
    @Size(max=255)
    private String lastName;
    @NotBlank
    @Size(max=255)
    private String city;
    private Long profileImageId;
    private String card;
}
