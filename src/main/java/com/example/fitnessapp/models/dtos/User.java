package com.example.fitnessapp.models.dtos;

import com.example.fitnessapp.models.entities.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Integer avatar;
    private String card;
    private String mail;
    private Integer cityId;
    private String cityName;
    private String active;
    private Integer diaryId;
    private String token;

   // private List<Comment> commentsById;
  //  private List<Message> messagesById;
  //  private List<Message> messagesById_0;
    //private List<Program> programsById;
 //   private List<Reply> repliesById;
   // private List<Status> statusesById;
//    private List<Subscription> subscriptionsById;
//    private City cityByCityId;
    private Diary diaryByDiaryId;
}
