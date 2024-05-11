package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.ProgramEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class Image {
    private Integer id;
    private String name;
    private String type;
    private Integer size;
    private byte[]data;

    // private Integer programId;
   // private Program programByProgramId;
}
