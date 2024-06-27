package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dtos.Mail;
import com.example.fitnessapp.services.MailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final MailService emailService;

    public EmailController(MailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public void sendEmail(@RequestParam String content, @RequestParam String receiver){
        this.emailService.sendAdvisorMail(content, receiver);
    }


}
