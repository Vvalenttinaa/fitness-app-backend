package com.example.fitnessapp.models.requests;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

@Data
public class ImageRequest {
    private String name;
}
