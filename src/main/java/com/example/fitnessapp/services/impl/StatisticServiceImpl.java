package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.controllers.UserController;
import com.example.fitnessapp.exceptions.AlreadyExistsException;
import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dtos.Statistic;
import com.example.fitnessapp.models.entities.DiaryEntity;
import com.example.fitnessapp.models.entities.StatisticEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.StatisticRequest;
import com.example.fitnessapp.repositories.DiaryRepository;
import com.example.fitnessapp.repositories.StatisticRepository;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.LoggerService;
import com.example.fitnessapp.services.StatisticService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {
    final ModelMapper mapper;
    final DiaryRepository diaryRepository;
    final StatisticRepository statisticRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final LoggerService loggerService;

    public StatisticServiceImpl(ModelMapper mapper, DiaryRepository diaryRepository, StatisticRepository statisticRepository, UserRepository userRepository, LoggerService loggerService) {
        this.mapper = mapper;
        this.diaryRepository = diaryRepository;
        this.statisticRepository = statisticRepository;
        this.userRepository = userRepository;
        this.loggerService = loggerService;
    }

    @Override
    public Statistic insertStatistic(StatisticRequest statisticRequest, Integer userId) {
        loggerService.addLog("Inserting statistic for user " + userId);


//        StatisticEntity statisticEntity = mapper.map(statisticRequest, StatisticEntity.class);
//    //    logger.error(statisticEntity.toString());
//        Integer diaryId = userRepository.findById(userId).get().getDiaryId();
//        DiaryEntity diaryEntity;
//        if(diaryId == null){
//            diaryEntity = diaryRepository.saveAndFlush(new DiaryEntity());
//            diaryId = diaryEntity.getId();
//            UserEntity userEntity = userRepository.findById(userId).get();
//            userEntity.setDiaryId(diaryId);
//            userEntity.setDiaryByDiaryId(diaryEntity);
//        }else{
//            diaryEntity = diaryRepository.findById(diaryId).get();
//        }
//   //     logger.error("DiaryId je " + diaryId);
//        statisticEntity.setDiaryByDiaryId(diaryEntity);
//        statisticEntity.setDiaryId(diaryEntity.getId());
//        LocalDate localDate = LocalDate.now();
//        Date date = Date.valueOf(localDate);
//        statisticEntity.setDate(date);
//   //     logger.debug(statisticEntity.toString());
//   //     logger.error(statisticEntity.toString());
//        return mapper.map(statisticRepository.saveAndFlush(statisticEntity), Statistic.class);
        StatisticEntity statisticEntity = mapper.map(statisticRequest, StatisticEntity.class);
        Integer diaryId = userRepository.findById(userId).get().getDiaryId();
        DiaryEntity diaryEntity;

        if (diaryId == null) {
            diaryEntity = diaryRepository.saveAndFlush(new DiaryEntity());
            diaryId = diaryEntity.getId();
            UserEntity userEntity = userRepository.findById(userId).get();
            userEntity.setDiaryId(diaryId);
            userEntity.setDiaryByDiaryId(diaryEntity);
        } else {
            diaryEntity = diaryRepository.findById(diaryId).get();
        }

        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);

        // Provjera postoji li zapis s današnjim datumom
        Optional<StatisticEntity> existingStatistic = statisticRepository.findByDateAndDiaryId(date, diaryId);
        if (existingStatistic.isPresent()) {
            throw new AlreadyExistsException();
        }

        statisticEntity.setDiaryByDiaryId(diaryEntity);
        statisticEntity.setDiaryId(diaryEntity.getId());
        statisticEntity.setDate(date);

        return mapper.map(statisticRepository.saveAndFlush(statisticEntity), Statistic.class);

    }

    @Override
    public List<Statistic> readStatistic(Integer id) {
        loggerService.addLog("Reading statistic for user " + id);

        Integer diaryId = userRepository.findById(id).orElseThrow(NotFoundException::new).getDiaryId();
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

    public byte[] generatePDF(List<Statistic> statistics) {
        loggerService.addLog("Generating file for download");

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Results of my work", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));


            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            addTableHeader(table);

            for (Statistic stat : statistics) {
                addRows(table, stat);
            }

            document.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Date", "Description", "Duration (min)", "Intensity", "Result", "Weight (kg)")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, Statistic stat) {
        table.addCell(stat.getDate().toString());
        table.addCell(stat.getDescription());
        table.addCell(stat.getDuration().toString());
        table.addCell(stat.getIntensity().toString());
        table.addCell(stat.getResult().toString());
        table.addCell(stat.getWeight().toString());
    }
}
