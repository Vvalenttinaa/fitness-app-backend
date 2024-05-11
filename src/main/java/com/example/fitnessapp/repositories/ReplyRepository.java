package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer> {
    List<ReplyEntity> findAllByCommentId(Integer id);
}
