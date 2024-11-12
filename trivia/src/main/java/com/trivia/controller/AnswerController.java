package com.trivia.controller;

import com.trivia.exception.DaoException;
import com.trivia.model.Answer;
import com.trivia.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/answer")
@RestController
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController (AnswerService answerService){
        this.answerService = answerService;
    }

    @GetMapping("")
    public List<Answer> list (){
        List<Answer> answers;

        try{
            answers = answerService.getAllAnswers();
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return answers;
    }

    @GetMapping("/question/{id}")
    public List<Answer> listAnswersByQuestionId(@PathVariable int id){
        List<Answer> answers;

        try{
            answers = answerService.getAnswersByQuestionId(id);

        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return answers;
    }

    @GetMapping("/{id}")
    public Answer getAnswer(@PathVariable int id){
        Answer answer;
        try{
            answer = answerService.getAnswerById(id);
            if (answer == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"); //Status Code: 404 = The given URL doesn't point to a valid resource
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return answer;
    }

    @PutMapping("/{id}")
    public Answer update(@Valid @RequestBody Answer updateAnswer, @PathVariable int id){
        Answer answer;

        try{
            updateAnswer.setId(id);
            answer = answerService.updateAnswer(updateAnswer);
            if (answer == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"); //Status Code: 404 = The given URL doesn't point to a valid resource
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return answer;
    }

    @PostMapping("")
    public Answer create(@Valid @RequestBody Answer newAnswer){
        Answer answer;

        try{
           answer = answerService.addAnswer(newAnswer);

        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return answer;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        try{
            int rowsDeleted = answerService.deleteAnswer(id);
            if (rowsDeleted == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"); //Status Code: 404 = The given URL doesn't point to a valid resource
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
    }
}
