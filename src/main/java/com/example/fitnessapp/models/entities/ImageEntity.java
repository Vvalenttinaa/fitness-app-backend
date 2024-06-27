package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "image", schema = "fitness_app_db", catalog = "")
public class ImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name="type",nullable = false)
    private String type;
    @Basic
    @Column(name="size",nullable = false)
    private Integer size;
    @Basic
    @Column(name = "program_id", insertable=false, updatable=false)
    private Integer programId;
    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private ProgramEntity programByProgramId;

}
