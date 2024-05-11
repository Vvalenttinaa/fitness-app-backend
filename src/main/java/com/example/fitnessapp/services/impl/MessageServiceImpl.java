package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Message;
import com.example.fitnessapp.models.entities.MessageEntity;
import com.example.fitnessapp.models.requests.MessageRequest;
import com.example.fitnessapp.repositories.MessageRepository;
import com.example.fitnessapp.repositories.UserRepository;
import com.example.fitnessapp.services.MessageService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    final ModelMapper mapper;
    final MessageRepository messageRepository;
    final UserRepository userRepository;

    public MessageServiceImpl(ModelMapper mapper, MessageRepository messageRepository, UserRepository userRepository) {
        this.mapper = mapper;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message sendMessage(MessageRequest message) {
        MessageEntity messageEntity = mapper.map(message, MessageEntity.class);
        messageEntity.setDateAndTime(Timestamp.valueOf(LocalDateTime.now()));
        messageEntity.setUserBySenderId(userRepository.findById(message.getSenderId()).get());
        messageEntity.setUserByReceiverId(userRepository.findById(message.getReceiverId()).get());
        messageEntity = messageRepository.saveAndFlush(messageEntity);
        return mapper.map(messageEntity, Message.class);
    }

    @Override
    public List<Message> getAllMyMesssages(Integer id) {
        List<MessageEntity> messageEntities = messageRepository.findAllByReceiverId(id);
        List<Message> messages = new ArrayList<>();
        for (MessageEntity m:messageEntities
             ) {
            messages.add(mapper.map(m, Message.class));
        }
        return messages;
    }

    @Override
    public List<Message> viewChat(Integer id, Integer idSender) {
        List<MessageEntity> messageEntities = messageRepository.findAllByReceiverIdAndSenderId(id, idSender);
        List<Message> messages = new ArrayList<>();
        for (MessageEntity m:messageEntities
        ) {
            messages.add(mapper.map(m, Message.class));
        }
        Collections.sort(messages, Comparator.comparing(Message::getDateAndTime)); //.reversed()
        return messages;
    }
}
