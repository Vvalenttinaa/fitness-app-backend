package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.dtos.User;
import com.example.fitnessapp.models.entities.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAll();
    Optional<UserEntity> findByUsername(String username);
    boolean existsByMail(String mail);
    Optional<UserEntity> findByMail(String email);
    List<UserEntity> findAllByActive(String active);
}
