package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "attribute_description", schema = "fitness_app_db", catalog = "")
public class AttributedescriptionEntity {
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "attribute_id", insertable=false, updatable=false)
    private Integer attributeId;
    @Basic
    @Column(name = "program_id", insertable=false, updatable=false)
    private Integer programId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "id", nullable = false)
    private AttributeEntity attributeByAttributeId;
    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id", nullable = false)
    private ProgramEntity programByProgramId;

}
