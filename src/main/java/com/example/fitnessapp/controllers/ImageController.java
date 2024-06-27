package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dtos.Image;
import com.example.fitnessapp.services.ImageService;
import com.example.fitnessapp.services.LoggerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/image")
public class ImageController {

    private final ImageService imageService;
    private final LoggerService loggerService;

    public ImageController(ImageService imageService, LoggerService loggerService) {
        this.imageService = imageService;
        this.loggerService = loggerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Image uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("programId") Integer programId) throws IOException{
        return this.imageService.setImage(file, programId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable Integer id) throws IOException {
        loggerService.addLog("Trazim sliku po id-ju programa" + id);
        var image = imageService.getImageByProgramId(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(image.getData());
    }

    @PostMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public Image uploadProfileImage(@RequestParam("image") MultipartFile file, @RequestParam("userId") Integer userId) throws IOException{
        return this.imageService.setProfileImage(file, userId);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> downloadProfileImage(@PathVariable Integer id) throws IOException {
        var image = imageService.getByImageId(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(image.getData());
    }
}
