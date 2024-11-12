package com.trivia.service;

import com.trivia.dao.QuestionDao;
import com.trivia.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestQuestionService implements QuestionService{

    private QuestionDao questionDao;

    public RestQuestionService(QuestionDao questionDao){
        this.questionDao =  questionDao;
    }


    @Override
    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    @Override
    public List<Question> getQuestionsByCategoryId(int categoryId) {
        return questionDao.getQuestionsByCategoryId(categoryId);
    }

    @Override
    public Question getQuestionById(int id) {
        return questionDao.getQuestionById(id);
    }

    @Override
    public Question updateQuestion(Question updateQuestion) {
        return questionDao.updateQuestion(updateQuestion);
    }

    @Override
    public Question addQuestions(Question newQuestion) {
        return questionDao.addQuestions(newQuestion);
    }

    @Override
    public int deleteQuestion(int id) {
        return questionDao.deleteQuestion(id);
    }
}
