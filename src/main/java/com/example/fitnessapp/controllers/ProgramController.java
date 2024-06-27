package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dtos.Comment;
import com.example.fitnessapp.models.dtos.Program;
import com.example.fitnessapp.models.dtos.Reply;
import com.example.fitnessapp.models.requests.CommentRequest;
import com.example.fitnessapp.models.requests.ProgramRequest;
import com.example.fitnessapp.models.requests.ReplyRequest;
import com.example.fitnessapp.models.requests.SearchRequest;
import com.example.fitnessapp.services.ProgramService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("api/program")
public class ProgramController {
    ProgramService programService;

    public ProgramController(ProgramService service) {
        this.programService = service;
    }

//    @GetMapping("")
//    public List<Program> getAll() {
//        return programService.findAll();
//    }

    @GetMapping("")
    public Page<Program> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return programService.findAll(page, size);
    }

    @GetMapping("/{id}")
    public Program findById(@PathVariable Integer id){
        return this.programService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Program create(@RequestBody ProgramRequest programRequest){
        return programService.insert(programRequest);
    }


    @GetMapping("/search")
    public Page<Program> searchPrograms(@RequestParam(required = false) String category,
                                        @RequestParam(required = false) String search,
                                        @RequestParam(required = false) String attribute,
                                        @RequestParam(required = false) String description,
                                        Pageable page)
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setCategory(category);
        searchRequest.setSearch(search);
        searchRequest.setAttribute(attribute);
        searchRequest.setDescription(description);
        return programService.searchProgram(searchRequest, page);
    }
    @PostMapping("/{id}/comments")
    public Comment commentFitnessProgram(@PathVariable Integer id, @Valid @RequestBody CommentRequest commentRequest){
        return this.programService.comment(commentRequest);
    }
    @PostMapping("/{id}/comments/reply")
    public Reply replyOnComment(@PathVariable Integer id, @Valid @RequestBody ReplyRequest replyRequest){
        return this.programService.reply(replyRequest);
    }
    @GetMapping("/comments/{id}/reply")
    public List<Reply> repliesOnComment(@PathVariable Integer id)
    {
        return this.programService.findAllRepliesByComment(id);
    }
    @GetMapping("/{id}/comments")
    public  List<Comment> findAllCommentsForProgram(@PathVariable Integer id){
        return this.programService.findAllCommentsByProgram(id);
    }
}
