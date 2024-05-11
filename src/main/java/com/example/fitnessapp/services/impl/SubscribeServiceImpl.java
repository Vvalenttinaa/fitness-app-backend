package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Subscription;
import com.example.fitnessapp.models.entities.SubscriptionEntity;
import com.example.fitnessapp.repositories.CategoryEntityRepository;
import com.example.fitnessapp.repositories.ProgramRepository;
import com.example.fitnessapp.repositories.SubscribeRepository;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.MailService;
import com.example.fitnessapp.services.SubscribeService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional
public class SubscribeServiceImpl implements SubscribeService {

    final
    SubscribeRepository subscribeRepository;
    final ProgramRepository programRepository;
    final CategoryEntityRepository categoryRepository;
    final UserRepository userRepository;
    final ModelMapper modelMapper;

    final MailService mailService;

    public SubscribeServiceImpl(SubscribeRepository subscribeRepository, ProgramRepository programRepository, CategoryEntityRepository categoryRepository, UserRepository userRepository, ModelMapper modelMapper, MailService mailService) {
        this.subscribeRepository = subscribeRepository;
        this.programRepository = programRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
    }

    @Override
    public Subscription subscribeToCategory(Integer userId, Integer categoryId){
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setCategoryId(categoryId);
        subscriptionEntity.setCategoryByCategoryId(categoryRepository.findCategoryEntityById(categoryId));
        subscriptionEntity.setUserId(userId);
        subscriptionEntity.setUserByUserId(userRepository.findById(userId).get());
        subscriptionEntity.setDate(Date.valueOf(LocalDate.now()));
        subscriptionEntity = subscribeRepository.saveAndFlush(subscriptionEntity);
        return modelMapper.map(subscriptionEntity, Subscription.class);
    }

    @Scheduled(cron="0 0 10 * * *")
    public void sendEmailNotification(){
        var subscribers=this.subscribeRepository.findAll();
        LocalDateTime current = LocalDateTime.now();
        var currentDate= Date.from(current.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime yesterDay=current.minusDays(1);
        var yesterdayDate=Date.from(yesterDay.atZone(ZoneId.systemDefault()).toInstant());
        for(var sub:subscribers){
            var fitnessPrograms=this.programRepository.findByCategoryIdAndCreatedAtBetween(sub.getCategoryId(), yesterdayDate, currentDate);
            if(!fitnessPrograms.isEmpty()) {
                StringBuilder builder = new StringBuilder("There are new fitness programs for you :\n");
                for (var fp : fitnessPrograms)
                    builder.append("\t" + fp.getName() + "\n");
                this.mailService.sendMailSubscription(builder.toString(),sub.getUserId());
            }
        }
    }
}
