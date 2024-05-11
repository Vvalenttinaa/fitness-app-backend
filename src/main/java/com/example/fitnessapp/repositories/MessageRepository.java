package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.MessageEntity;
import io.swagger.models.auth.In;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByReceiverId(Integer id);
    List<MessageEntity> findAllByReceiverIdAndSenderId(Integer receiverId, Integer senderId);
}
