package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dtos.Mail;
import com.example.fitnessapp.services.MailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final MailService emailService;

    public EmailController(MailService emailService) {
        this.emailService = emailService;
    }

//    @GetMapping
//    public void send(){
//        Mail m = new Mail();
//        m.setMailSubject("test");
//        m.setMailTo("valentinabozic251@gmail.com");
//        m.setMailContent("evo me stigo sam");
//        this.emailService.sendEmail(m);
//    }
///*
//    @PostMapping
//    public void sendEmail(@RequestBody Email email){
//        this.emailService.sendAdvisorMail(email);
//    }


}
