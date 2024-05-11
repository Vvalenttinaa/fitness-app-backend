package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString(exclude = {"userByUserId", "categoryByCategoryId"})
@Entity
@Table(name = "subscription", schema = "fitness_app_db", catalog = "")
public class SubscriptionEntity {
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "user_id", insertable=false, updatable=false)
    private Integer userId;
    @Basic
    @Column(name = "category_id", insertable=false, updatable=false)
    private Integer categoryId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByUserId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity categoryByCategoryId;
}
