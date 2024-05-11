package com.example.fitnessapp.models.dtos;

import lombok.Data;

import java.util.List;

@Data
public class Program {
    private Integer id;
    private String name;
    private List<Image> images;
    private Double price;

    private String description;
    private Integer level;
    private Integer duration;
    private String contact;

    private String locationName;
  //  private Integer locationId;
    private String instructorName;
    private Integer categoryId;
    private String categoryName;
  //  private List<Attribute> categoryAttributes;
  //  private Category category;
    private List<AttributeDescription> attributedescriptionsById;
  //  private List<Comment> commentsById;
  //  private List<Image> imagesById;
  //  private Location locationByLocationId;
  //  private Instructor instructorByInstructorId;
  //  private Category categoryByCategoryId;
   // private List<Status> statusesById;
   // private List<User> usersById;
}
