package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Message;
import com.example.fitnessapp.models.requests.MessageRequest;

import java.util.List;

public interface MessageService {
    Message sendMessage(MessageRequest message);
    List<Message> getAllMyMesssages(Integer id);
    List<Message> viewChat(Integer id, Integer idSender);
}
