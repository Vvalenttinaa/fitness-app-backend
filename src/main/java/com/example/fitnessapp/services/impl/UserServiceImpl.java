package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.AlreadyExistsException;
import com.example.fitnessapp.models.dtos.Comment;
import com.example.fitnessapp.models.dtos.Program;
import com.example.fitnessapp.models.dtos.Status;
import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.models.entities.PaymentMethodEntity;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.StatusEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.StatusRequest;
import com.example.fitnessapp.repositories.PaymentMethodRepository;
import com.example.fitnessapp.repositories.ProgramRepository;
import com.example.fitnessapp.repositories.StatusRepository;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final ProgramRepository programRepository;
    final StatusRepository statusRepository;
    final PaymentMethodRepository paymentMethodRepository;
    final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository userRepository, ProgramRepository programRepository, StatusRepository statusRepository, PaymentMethodRepository paymentMethodRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.statusRepository = statusRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User getById(Integer id) {
        return modelMapper.map(userRepository.findById(id), User.class);
    }

    @Override
    public List<Program> getAllMyPrograms(Integer id) {
        List<StatusEntity> statuses = statusRepository.findAllByUserId(id);
        List<Program> programs = new ArrayList<>();
        for (StatusEntity s: statuses
             ) {
            if(!"deleted".equals(s.getState())) {
                programs.add(modelMapper.map(programRepository.findById(s.getProgramId()).get(), Program.class));
            }
        }
        return programs;
    }

    @Override
    public Status startProgram(StatusRequest request) {
        if(statusRepository.existsByUserIdAndProgramId(request.getUserId(), request.getProgramId())){
            throw new AlreadyExistsException();
        }
        StatusEntity statusEntity = modelMapper.map(request, StatusEntity.class);
        statusEntity.setCretaedAt(Date.valueOf(LocalDate.now()));
        logger.error(statusEntity.toString());
        PaymentMethodEntity pme = paymentMethodRepository.findById(request.getPaymentMethodId()).get();
        statusEntity.setPaymentMethodId(request.getPaymentMethodId());
        statusEntity.setPaymentMethodByPaymentMethodId(pme);
        ProgramEntity pe = programRepository.findById(request.getProgramId()).get();
        statusEntity.setProgramId(request.getProgramId());
        statusEntity.setProgramByProgramId(pe);
        UserEntity ue = userRepository.findById(request.getUserId()).get();
        statusEntity.setUserId(request.getUserId());
        statusEntity.setUserByUserId(ue);
        return modelMapper.map(statusRepository.saveAndFlush(statusEntity), Status.class);
    }

    @Override
    public void delete(Integer programId, Integer userId){
        StatusEntity status = statusRepository.findByUserIdAndProgramId(userId, programId);
        status.setState("deleted");
        statusRepository.saveAndFlush(status);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAllByActive("true").stream()
                .map(userEntity -> modelMapper.map(userEntity, User.class))
                .collect(Collectors.toList());
    }
}
