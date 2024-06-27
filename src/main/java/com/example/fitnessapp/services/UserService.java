package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Program;
import com.example.fitnessapp.models.dtos.Status;
import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.models.requests.EditUserRequest;
import com.example.fitnessapp.models.requests.StatusRequest;

import java.util.List;

public interface UserService {
    User getById(Integer id);
    List<Program> getAllMyPrograms(Integer id);
    Status startProgram(StatusRequest request);
    void delete(Integer programId, Integer userId);
    User edit(Integer userId, EditUserRequest userRequest);
    List<User> findAll();
    boolean insertCard(Integer userId, String card);
}
