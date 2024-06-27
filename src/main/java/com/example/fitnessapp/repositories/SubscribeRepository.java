package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.dtos.Subscription;
import com.example.fitnessapp.models.entities.SubscriptionEntity;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscriptionEntity, Integer> {
    List<SubscriptionEntity> findAllByUserId(Integer userId);
}
