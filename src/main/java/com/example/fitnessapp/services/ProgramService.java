package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Comment;
import com.example.fitnessapp.models.dtos.Program;
import com.example.fitnessapp.models.dtos.Reply;
import com.example.fitnessapp.models.dtos.Status;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.requests.CommentRequest;
import com.example.fitnessapp.models.requests.ProgramRequest;
import com.example.fitnessapp.models.requests.ReplyRequest;
import com.example.fitnessapp.models.requests.SearchRequest;
import org.springframework.data.domain.Page;
import  org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProgramService {
    Program insert(ProgramRequest programRequest);

    Page<Program> findAll(int page, int size);

    Program findById(Integer id);
  //  List<Program> searchProgram(SearchRequest searchRequest);

//    Page<Program> searchProgram(SearchRequest searchRequest, int page, int size);

    Page<Program> searchProgram(SearchRequest searchRequest, Pageable pageable);

    Comment comment(CommentRequest commentRequest);
    List<Comment> findAllCommentsByProgram(Integer id);
    List<Reply> findAllRepliesByComment(Integer id);
    Reply reply(ReplyRequest replyRequest);

    Status findStatus(Integer userId, Integer programId);
}
