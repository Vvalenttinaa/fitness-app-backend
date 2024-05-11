package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Data
@ToString
@Table(name = "message", schema = "fitness_app_db", catalog = "")
public class MessageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "date_and_time")
    private Timestamp dateAndTime;
    @Basic
    @Column(name = "sender_id", insertable=false, updatable=false)
    private Integer senderId;
    @Basic
    @Column(name = "receiver_id", insertable=false, updatable=false)
    private Integer receiverId;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private UserEntity userBySenderId;
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByReceiverId;

}
