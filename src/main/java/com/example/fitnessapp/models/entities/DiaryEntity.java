package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name = "diary", schema = "fitness_app_db", catalog = "")
public class DiaryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "diaryByDiaryId")
    private List<StatisticEntity> statisticsById;
    @OneToMany(mappedBy = "diaryByDiaryId")
    private List<UserEntity> usersById;
    @Override
    public String toString() {
        return "DiaryEntity{" +
                "id=" + id +
                '}';
    }
}
