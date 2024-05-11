package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Entity
@ToString
@Data
@Table(name = "status", schema = "fitness_app_db", catalog = "")
public class StatusEntity {
    @Basic
    @Column(name = "cretaed_at")
    private Date cretaedAt;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "program_id", insertable=false, updatable=false)
    private Integer programId;
    @Basic
    @Column(name = "user_id", insertable=false, updatable=false)
    private Integer userId;
    @Basic
    @Column(name = "payment_method_id", insertable=false, updatable=false)
    private Integer paymentMethodId;
    @Basic
    @Column(name = "paid")
    private Byte paid;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "program_id", referencedColumnName = "id", nullable = false)
    private ProgramEntity programByProgramId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByUserId;
    @ManyToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id", nullable = false)
    private PaymentMethodEntity paymentMethodByPaymentMethodId;
    @Override
    public String toString() {
        return "StatusEntity{" +
                "id=" + id +
                ", createdAt=" + cretaedAt +
                ", programId=" + programId +
                ", userId=" + userId +
                ", paymentMethodId=" + paymentMethodId +
                ", paid=" + paid +
                ", state=" + state +

                '}';
    }
}
