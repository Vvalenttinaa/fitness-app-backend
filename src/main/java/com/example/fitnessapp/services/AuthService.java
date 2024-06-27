package com.example.fitnessapp.services;
import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegisterRequest;


public interface AuthService {
    User register(RegisterRequest request);
    User login(LoginRequest request);
    boolean activateAccount(Integer id);
    void resendActivationMail(LoginRequest loginRequest);
   // void changePassword(PasswordChange password, Authentication authentication);
    User update(User request);
}
