package com.example.fitnessapp.models.dtos;

import lombok.Data;

@Data
public class ResultsPdf {
    private String fileName;
    private byte[] data;
}
