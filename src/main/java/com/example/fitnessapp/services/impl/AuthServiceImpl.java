package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.*;
import com.example.fitnessapp.models.dtos.Mail;
import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.models.entities.CityEntity;
import com.example.fitnessapp.models.entities.DiaryEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegisterRequest;
import com.example.fitnessapp.repositories.CityRepository;
import com.example.fitnessapp.repositories.DiaryRepository;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.AuthService;
import com.example.fitnessapp.services.LoggerService;
import com.example.fitnessapp.services.MailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final DiaryRepository diaryRepository;
    private final ModelMapper mapper;
    private final MailService emailService;
    private final HttpServletRequest request;
    private final LoggerService loggerService;

    @PersistenceContext
    private EntityManager entityManager;

    public AuthServiceImpl(UserRepository userRepository, CityRepository cityRepository, DiaryRepository diaryRepository, ModelMapper mapper, MailService emailService, HttpServletRequest request, LoggerService loggerService) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.diaryRepository = diaryRepository;
        this.mapper = mapper;
        this.emailService = emailService;
        this.request = request;
        this.loggerService = loggerService;
    }

    @Override
    public User register(RegisterRequest request) {
        loggerService.addLog("Registration for user " + request.getFirstName());
        if (userRepository.existsByMail(request.getMail())) {
            loggerService.addLog("User already exists");
            throw new AlreadyExistsException();
        }
        UserEntity entity = mapper.map(request, UserEntity.class);
        Boolean cityExists = cityRepository.existsByName(request.getCity());
        if(cityExists) {
            CityEntity cityEntity = cityRepository.findByName(request.getCity());
            entity.setCityId(cityEntity.getId());
            entity.setCityByCityId(cityEntity);
        }else{
            CityEntity city = new CityEntity();
            city.setName(request.getCity());
            city=cityRepository.saveAndFlush(city);
            entity.setCityId(city.getId());
            entity.setCityByCityId(city);
        }
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity = diaryRepository.saveAndFlush(diaryEntity);
        entity.setDiaryId(diaryEntity.getId());
        entity.setDiaryByDiaryId(diaryEntity);
        entity.setPassword(request.getPassword());
        entity.setActive("false");
        entity.setMail(request.getMail());
        entity = userRepository.saveAndFlush(entity);
        entityManager.refresh(entity);
        Mail m = new Mail();
        m.setMailTo(entity.getMail());
        m.setMailSubject("Verifikacija");
        m.setMailFrom("valentinabozic251@gmail.com");
        emailService.sendEmail(m, entity.getId());
        return mapper.map(entity, User.class);
    }


    @Override
    public User login(LoginRequest request) {
        loggerService.addLog("Login for " + request.getUsername());
        UserEntity userEntity = null;
            userEntity = userRepository.findByUsername(request.getUsername()).orElseThrow(NotFoundException::new);
            User user = mapper.map(userEntity, User.class);
            user.setToken("true");
        if (userEntity.getActive().equals("blocked")) {
            loggerService.addLog("Account is blocked");
            throw new AccountBlockedException();
        }
        if (userEntity.getActive().equals("false")) {
            loggerService.addLog("Account is not active");
           // resendActivationMail(userEntity);
//            throw new NotApprovedException();
        }
        return user;
    }


    @Override
    public boolean activateAccount(Integer id) {
        if(id == null) {
            return false;
        }
        loggerService.addLog("Account activation");

        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.setActive("true");
        userRepository.saveAndFlush(userEntity);
        return true;
    }

    @Override
    public void resendActivationMail(LoginRequest loginRequest) {

        UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(NotFoundException::new);
        User user = null;
        if(userEntity.getPassword().equals(loginRequest.getPassword())) {
            user = mapper.map(userEntity, User.class);
        }else{
            return;
        }

        loggerService.addLog("Resending activation mail to " + user.getFirstName());

        Mail m = new Mail();
        m.setMailTo(user.getMail());
        m.setMailSubject("Reactivation");
        m.setMailFrom("valentinabozic251@gmail.com");
        this.emailService.sendEmail(m, user.getId());
    }

        @Override
        public User update(User request) {
            loggerService.addLog("Updating user " + request.getFirstName());

            UserEntity userOld = userRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException());
            if (!Objects.equals(request.getFirstName(), userOld.getFirstName())) {
                userOld.setFirstName(request.getFirstName());
            }
            if (!Objects.equals(request.getLastName(), userOld.getLastName())) {
                userOld.setLastName(request.getLastName());
            }
            if (!Objects.equals(request.getUsername(), userOld.getUsername())) {
                userOld.setUsername(request.getUsername());
            }
            if (!Objects.equals(request.getPassword(), userOld.getPassword())) {
                userOld.setPassword(request.getPassword());
            }
            if (!Objects.equals(request.getCard(), userOld.getCard())) {
                userOld.setCard(request.getCard());
            }
            if (!Objects.equals(request.getMail(), userOld.getMail())) {
                userOld.setMail(request.getMail());
            }
            UserEntity updatedUserEntity = userRepository.saveAndFlush(userOld);
            return mapper.map(updatedUserEntity, User.class);
        }

}