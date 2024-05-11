package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString(exclude = {"comments", "messagesById", "messagesById_0", "replies", "statusesById", "subscriptionsById", "cityByCityId", "diaryByDiaryId"})
@Table(name = "user", schema = "fitness_app_db", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @Basic
    @Column(name = "city_id", insertable=false, updatable=false)
    private Integer cityId;
    @Basic
    @Column(name = "card")
    private String card;
    @Basic
    @Column(name = "active")
    private String active;
    @Basic
    @Column(name="token",nullable = false,length = 100)
    private String token;
    @Basic
    @Column(name="mail",nullable = false)
    private String mail;
    @Basic
    @Column(name = "diary_id", insertable=false, updatable=false)
    private Integer diaryId;
    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "userBySenderId")
    private List<MessageEntity> messagesById;
    @OneToMany(mappedBy = "userByReceiverId")
    private List<MessageEntity> messagesById_0;
    @OneToMany(mappedBy = "user")
    private List<ReplyEntity> replies;
    @OneToMany(mappedBy = "userByUserId")
    private List<StatusEntity> statusesById;
    @OneToMany(mappedBy = "userByUserId")
    private List<SubscriptionEntity> subscriptionsById;
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private CityEntity cityByCityId;
    @ManyToOne
    @JoinColumn(name = "diary_id", referencedColumnName = "id", nullable = false)
    private DiaryEntity diaryByDiaryId;

}
