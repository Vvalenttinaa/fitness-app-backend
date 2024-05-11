package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name = "payment_method", schema = "fitness_app_db", catalog = "")
public class PaymentMethodEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "paymentMethodByPaymentMethodId")
    private List<StatusEntity> statusesById;
    @Override
    public String toString() {
        return "PaymentMethodEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
