package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.AlreadyExistsException;
import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dtos.Program;
import com.example.fitnessapp.models.dtos.Status;
import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.models.entities.*;
import com.example.fitnessapp.models.requests.EditUserRequest;
import com.example.fitnessapp.models.requests.StatusRequest;
import com.example.fitnessapp.repositories.*;
import com.example.fitnessapp.services.LoggerService;
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
    final CityRepository cityRepository;
    final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final LoggerService loggerService;


    public UserServiceImpl(UserRepository userRepository, ProgramRepository programRepository, StatusRepository statusRepository, PaymentMethodRepository paymentMethodRepository, CityRepository cityRepository, ModelMapper modelMapper, LoggerService loggerService) {
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.statusRepository = statusRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.loggerService = loggerService;
    }

    @Override
    public User getById(Integer id) {
        loggerService.addLog("Find user by id " + id);
        return modelMapper.map(userRepository.findById(id).orElseThrow(NotFoundException::new), User.class);
    }

    @Override
    public List<Program> getAllMyPrograms(Integer id) {
        loggerService.addLog("Get all programs for user " + id);

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
        loggerService.addLog("Starting new program" + request.getUserId() + " " + request.getProgramId());

        if(statusRepository.existsByUserIdAndProgramId(request.getUserId(), request.getProgramId())){
            throw new AlreadyExistsException();
        }
        StatusEntity statusEntity = modelMapper.map(request, StatusEntity.class);
        statusEntity.setCretaedAt(Date.valueOf(LocalDate.now()));
        logger.error(statusEntity.toString());
        PaymentMethodEntity pme = paymentMethodRepository.findById(request.getPaymentMethodId()).get();
        statusEntity.setPaymentMethodId(request.getPaymentMethodId());
        statusEntity.setPaymentMethodByPaymentMethodId(pme);
        if(request.getPaymentMethodId() == 3){
            statusEntity.setPaid(null);
        }
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
        loggerService.addLog("Deleting program participation" + programId);

        StatusEntity status = statusRepository.findByUserIdAndProgramId(userId, programId);
        status.setState("deleted");
        statusRepository.saveAndFlush(status);
    }

    @Override
    public User edit(Integer userId, EditUserRequest userRequest) {
        loggerService.addLog("Editing user request " + userId);

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundException:: new);
        if(userRequest.getFirstName() != null){
            userEntity.setFirstName(userRequest.getFirstName());
        }
        if(userRequest.getLastName() != null){
            userEntity.setLastName(userRequest.getLastName());
        }
        if(userRequest.getPassword() != null){
            userEntity.setPassword(userRequest.getPassword());
        }
        if(userRequest.getMail() != null){
            userEntity.setMail(userRequest.getMail());
        }
        if(userRequest.getCity() != null){
            if(cityRepository.existsByName(userRequest.getCity())) {
                CityEntity cityEntity = cityRepository.findByName(userRequest.getCity());
                userEntity.setCityByCityId(cityEntity);
                userEntity.setCityId(cityEntity.getId());
            }else{
                CityEntity cityEntity = new CityEntity();
                cityEntity.setName(userRequest.getCity());
                cityEntity = cityRepository.saveAndFlush(cityEntity);
                userEntity.setCityId(cityEntity.getId());
                userEntity.setCityByCityId(cityEntity);
            }
        }
        if(userRequest.getCard() != null){
            userEntity.setCard(userRequest.getCard());
        }
        userEntity = userRepository.saveAndFlush(userEntity);
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public List<User> findAll(){
        loggerService.addLog("Finding all programs");
        return userRepository.findAllByActive("true").stream()
                .map(userEntity -> modelMapper.map(userEntity, User.class))
                .collect(Collectors.toList());
    }

    public boolean insertCard(Integer userId, String card){
        loggerService.addLog("Inserting card for user " + userId);
        String cardOld = null;
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        cardOld = userEntity.getCard();
        userEntity.setCard(card);
        userEntity = userRepository.saveAndFlush(userEntity);
        if(userEntity.getCard().equals(card)){
            return true;
        }else{
            return false;
        }

    }

}
