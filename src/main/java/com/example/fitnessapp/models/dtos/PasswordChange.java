package com.example.fitnessapp.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChange {
    public class ChangePasswordDTO {
        @NotNull
        private Long id;

        @NotBlank
        private String oldPassword;

        @NotBlank
        @Size(min=8)
  //      @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}", message="Password must contain at least 8 characters, of which at least one number and one capital letter!")
        private String newPassword;
    }
}
