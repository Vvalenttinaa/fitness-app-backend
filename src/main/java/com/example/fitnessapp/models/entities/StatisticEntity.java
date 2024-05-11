package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
@Entity
@Table(name = "statistic", schema = "fitness_app_db", catalog = "")
public class StatisticEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "duration")
    private Integer duration;
    @Basic
    @Column(name = "intensity")
    private Integer intensity;
    @Basic
    @Column(name = "result")
    private Integer result;
    @Basic
    @Column(name = "weight")
    private Double weight;
    @Basic
    @Column(name = "diary_id", insertable=false, updatable=false)
    private Integer diaryId;
    @ManyToOne
    @JoinColumn(name = "diary_id", referencedColumnName = "id", nullable = false)
    private DiaryEntity diaryByDiaryId;
}
