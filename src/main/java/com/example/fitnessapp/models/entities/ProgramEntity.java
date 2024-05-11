package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
@Data
@Entity
@ToString
@Table(name = "program", schema = "fitness_app_db", catalog = "")
public class ProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "price")
    private Double price;
    @Basic
    @Column(name = "level")
    private Integer level;
    @Basic
    @Column(name = "duration")
    private Integer duration;
    @Basic
    @Column(name = "contact")
    private String contact;
    @Basic
    @Column(name = "createdAt")
    private Date createdAt;
    @Basic
    @Column(name = "location_id", insertable=false, updatable=false)
    private Integer locationId;
    @Basic
    @Column(name = "instructor_id", insertable=false, updatable=false)
    private Integer instructorId;
    @Basic
    @Column(name = "category_id", insertable=false, updatable=false)
    private Integer categoryId;
    @OneToMany(mappedBy = "programByProgramId")
    private List<AttributedescriptionEntity> attributedescriptionsById;
    @OneToMany(mappedBy = "program")
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "programByProgramId")
    private List<ImageEntity> imagesById;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private LocationEntity locationByLocationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false)
    private InstructorEntity instructorByInstructorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity categoryByCategoryId;
    @OneToMany(mappedBy = "programByProgramId")
    private List<StatusEntity> statusesById;
    @Override
    public String toString() {
        return "ProgramEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", level=" + level +
                ", duration=" + duration +
                ", contact='" + contact + '\'' +
                ", createdAt=" + createdAt +
                ", locationId=" + locationId +
                ", instructorId=" + instructorId +
                ", categoryId=" + categoryId +
                '}';
    }
}
