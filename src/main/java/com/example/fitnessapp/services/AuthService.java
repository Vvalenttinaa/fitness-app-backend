package com.example.fitnessapp.services;
import com.example.fitnessapp.models.dtos.PasswordChange;
import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegisterRequest;


public interface AuthService {
    void register(RegisterRequest request);
    User login(LoginRequest request);
    boolean activateAccount(Integer id);
    void resendActivationMail(UserEntity user);
   // void changePassword(PasswordChange password, Authentication authentication);
    User update(User request);
}
