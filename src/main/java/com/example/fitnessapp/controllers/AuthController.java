package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegisterRequest;
import com.example.fitnessapp.services.AuthService;
import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.services.MailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;
    private final MailService emailService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthService authService, MailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerClient(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received registration request: {}", request);

        try {
            return this.authService.register(request);
       //     logger.info("Registration successful for user: {}", request.getUsername());
        } catch (Exception e) {
            logger.error("Error occurred during registration:", e);
            throw e;
        }
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<Void> activateAccount(@PathVariable Integer id) {
        boolean activated = authService.activateAccount(id);
        if (activated) {
            URI redirectUri = URI.create("http://localhost:4200/registration?activated=true");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } else {
            URI redirectUri = URI.create("http://localhost:4200/registration?error=invalid");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }
    }

    @PostMapping("/resendEmail")
        public void resendEmail(@RequestBody LoginRequest loginRequest){
            authService.resendActivationMail(loginRequest);
    }

    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginRequest request) {
        return this.authService.login(request);
    }

    @PutMapping("/books")
    private User update(@RequestBody User request)
    {
        return this.authService.update(request);
    }
}