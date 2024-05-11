package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.controllers.UserController;
import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dtos.Statistic;
import com.example.fitnessapp.models.entities.DiaryEntity;
import com.example.fitnessapp.models.entities.StatisticEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.StatisticRequest;
import com.example.fitnessapp.repositories.DiaryRepository;
import com.example.fitnessapp.repositories.StatisticRepository;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.StatisticService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {
    final ModelMapper mapper;
    final DiaryRepository diaryRepository;
    final StatisticRepository statisticRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public StatisticServiceImpl(ModelMapper mapper, DiaryRepository diaryRepository, StatisticRepository statisticRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.diaryRepository = diaryRepository;
        this.statisticRepository = statisticRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Statistic insertStatistic(StatisticRequest statisticRequest, Integer userId) {

        StatisticEntity statisticEntity = mapper.map(statisticRequest, StatisticEntity.class);
    //    logger.error(statisticEntity.toString());
        Integer diaryId = userRepository.findById(userId).get().getDiaryId();
        DiaryEntity diaryEntity;
        if(diaryId == null){
            diaryEntity = diaryRepository.saveAndFlush(new DiaryEntity());
            diaryId = diaryEntity.getId();
            UserEntity userEntity = userRepository.findById(userId).get();
            userEntity.setDiaryId(diaryId);
            userEntity.setDiaryByDiaryId(diaryEntity);
        }else{
            diaryEntity = diaryRepository.findById(diaryId).get();
        }
   //     logger.error("DiaryId je " + diaryId);
        statisticEntity.setDiaryByDiaryId(diaryEntity);
        statisticEntity.setDiaryId(diaryEntity.getId());
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        statisticEntity.setDate(date);
   //     logger.debug(statisticEntity.toString());
   //     logger.error(statisticEntity.toString());
        return mapper.map(statisticRepository.saveAndFlush(statisticEntity), Statistic.class);
    }

    @Override
    public List<Statistic> readStatistic(Integer id) {
        Integer diaryId = userRepository.findById(id).get().getDiaryId();
     //   logger.error("diary id je " + diaryId);
        if(diaryId == null)
            throw new NotFoundException();
        List<StatisticEntity> statisticEntities = statisticRepository.findAllByDiaryId(diaryId);
        List<Statistic> statistics = new ArrayList<>();
        for (StatisticEntity s:statisticEntities
             ) {
       //     logger.error(s.toString());
            statistics.add(mapper.map(s, Statistic.class));
        }
        return statistics;
    }
}
