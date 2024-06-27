package com.example.fitnessapp.controllers;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dtos.*;
import com.example.fitnessapp.models.requests.*;
import com.example.fitnessapp.services.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    MessageService messageService;
    StatisticService statisticService;
    ProgramService fitnessProgramService;
    SubscribeService subscribeService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, MessageService messageService, StatisticService statisticService, ProgramService fitnessProgramService, SubscribeService subscribeService){
        this.userService=userService;
        this.messageService=messageService;
        this.statisticService=statisticService;
        this.fitnessProgramService = fitnessProgramService;
        this.subscribeService = subscribeService;
    }

    @GetMapping("")
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}/programs")
    public List<Program> getAllMyProgramas(@PathVariable Integer id){
        return userService.getAllMyPrograms(id);
    }

    @GetMapping("/{id}/details")
    public User getDetails(@PathVariable Integer id){
        return userService.getById(id);
    }

    @PutMapping("/{id}/details")
    public User edit(@PathVariable Integer id, @Valid @RequestBody EditUserRequest userRequest){
        return userService.edit(id, userRequest);
    }

    @PostMapping("/{id}/startProgram")
    public Status startProgram(@PathVariable Integer id, @Valid @RequestBody StatusRequest statusRequest){
        logger.debug("Pozvan startProgram za korisnika sa ID {}", id);
        if(userService.getById(id).getId() == null){
            throw new NotFoundException();
        }
        return userService.startProgram(statusRequest);
    }
    @PostMapping("/{id}/sendMessage")
    public Message sendMessage(@PathVariable Integer id, @Valid @RequestBody MessageRequest message){
        return messageService.sendMessage(message);
    }
    @GetMapping("/{id}/messages")
    public List<Message> viewMessages(@PathVariable Integer id)
    {
        return messageService.getAllMyMesssages(id);
    }

    @GetMapping("{id}/messages/{idSender}")
    public  List<Message> viewChat(@PathVariable Integer id, @PathVariable Integer idSender){
        return  messageService.viewChat(id, idSender);
    }
    @PostMapping("{id}/statistic")
    public Statistic addStatistic(@RequestBody @Valid StatisticRequest statisticRequest, @PathVariable Integer id) {
        return statisticService.insertStatistic(statisticRequest,id);
    }
    @GetMapping("{id}/statistic")
    public List<Statistic> readStatistic(@PathVariable Integer id){
        return statisticService.readStatistic(id);
    }

    @GetMapping("{id}/statistic/download")
    public ResponseEntity<byte[]> generatePDF(@PathVariable Integer id) {
        List<Statistic> statistics = statisticService.readStatistic(id);

        byte[] pdfBytes = statisticService.generatePDF(statistics);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Izvestaj.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @PostMapping("/{id}/fitness-program/{categoryId}/subscribe")
    public Subscription subscribeToProgram(@PathVariable Integer id,@PathVariable Integer categoryId){
        return this.subscribeService.subscribeToCategory(id, categoryId);
    }

    @GetMapping("/{id}/fitness-program/subscriptions")
    public List<Subscription> getAllSubscriptions(@PathVariable Integer id)
    {
        return this.subscribeService.getAllByUser(id);
    }
    @DeleteMapping("/{idUser}/program/{idProgram}")
    public void delete(@PathVariable Integer idUser, @PathVariable Integer idProgram){
         this.userService.delete(idProgram, idUser);
    }

    @GetMapping("/{idUser}/program/{idProgram}/status")
    public Status getStatus(@PathVariable Integer idUser, @PathVariable Integer idProgram){
        return this.fitnessProgramService.findStatus(idUser, idProgram);
    }

    @PostMapping("/{idUser}/insertCard")
    public boolean insertCard(@PathVariable Integer idUser, @RequestBody Card card){
        logger.error(card.getNumber());
       return userService.insertCard(idUser, card.getNumber());
    }
}

