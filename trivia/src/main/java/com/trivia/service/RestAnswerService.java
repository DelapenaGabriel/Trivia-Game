package com.trivia.service;

import com.trivia.dao.AnswerDao;
import com.trivia.model.Answer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class RestAnswerService implements AnswerService{
    private AnswerDao answerDao;

    public RestAnswerService(AnswerDao answerDao){
        this.answerDao = answerDao;
    }
    @Override
    public List<Answer> getAllAnswers() {
        return answerDao.getAllAnswers();
    }

    @Override
    public List<Answer> getAnswersByQuestionId(int questionId) {
        return answerDao.getAnswersByQuestionId(questionId);
    }

    @Override
    public Answer getAnswerById(int id) {
        return answerDao.getAnswerById(id);
    }

    @Override
    public Answer updateAnswer(Answer updateAnswer) {
        return answerDao.updateAnswer(updateAnswer);
    }

    @Override
    public Answer addAnswer(Answer newAnswer) {
        return answerDao.addAnswer(newAnswer);
    }

    @Override
    public int deleteAnswer(int id) {
        return answerDao.deleteAnswer(id);
    }
}
