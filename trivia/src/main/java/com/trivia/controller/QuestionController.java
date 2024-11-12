package com.trivia.controller;

import com.trivia.exception.DaoException;
import com.trivia.model.Question;
import com.trivia.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/question")
@RestController
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("")
    public List<Question> listAll (){
        List<Question> questions;

        try{
            questions = questionService.getAllQuestions();
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return questions;
    }

    @GetMapping("/category/{id}")
    public List<Question> listByCategoryId(@PathVariable int id){
        List<Question> questions;

        try{
            questions = questionService.getQuestionsByCategoryId(id);
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return questions;
    }

    @GetMapping("/{id}")
    public Question getQuestion (@PathVariable int id){
        Question question;
        try{
            question = questionService.getQuestionById(id);
            if (question == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return question;
    }

    @PutMapping("/{id}")
    public Question update(@Valid @RequestBody Question updateQuestion, @PathVariable int id){
        Question question;
        try{
            updateQuestion.setId(id);
            question = questionService.updateQuestion(updateQuestion);
        }catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"); //Status Code: 404 = The given URL doesn't point to a valid resource
        }
        return question;
    }

    @PostMapping("")
    public Question create(@Valid @RequestBody Question newQuestion){
        Question question;
        try{
            question = questionService.addQuestions(newQuestion);
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return question;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        try{
            int rowsDeleted = questionService.deleteQuestion(id);
            if (rowsDeleted == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
    }
}
