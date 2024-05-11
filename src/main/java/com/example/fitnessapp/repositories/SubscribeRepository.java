package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscriptionEntity, Integer> {
}
