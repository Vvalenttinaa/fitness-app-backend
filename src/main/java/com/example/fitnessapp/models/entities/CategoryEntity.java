package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name = "category", schema = "fitness_app_db", catalog = "")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "categoryByCategoryId")
    private List<AttributeEntity> attributesById;
    @OneToMany(mappedBy = "categoryByCategoryId")
    private List<ProgramEntity> programsById;
    @OneToMany(mappedBy = "categoryByCategoryId")
    private List<SubscriptionEntity> subscriptionsById;
}
