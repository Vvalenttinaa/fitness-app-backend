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
    public void registerClient(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received registration request: {}", request);

        try {
            this.authService.register(request);
            logger.info("Registration successful for user: {}", request.getUsername());
        } catch (Exception e) {
            logger.error("Error occurred during registration:", e);
            throw e; // ili bilo koju drugu odgovarajuću grešku
        }
    }

    @GetMapping("/activate/{id}")
    public Boolean activateAccount(@PathVariable Integer id){
        return this.authService.activateAccount(id);
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