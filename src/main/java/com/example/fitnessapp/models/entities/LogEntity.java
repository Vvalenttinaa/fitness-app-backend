package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "log", schema = "fitness_app_db", catalog = "")
public class LogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "log")
    private String log;
    @Basic
    @Column(name = "date")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntity logEntity = (LogEntity) o;

        if (id != null ? !id.equals(logEntity.id) : logEntity.id != null) return false;
        if (log != null ? !log.equals(logEntity.log) : logEntity.log != null) return false;
        if (date != null ? !date.equals(logEntity.date) : logEntity.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (log != null ? log.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
