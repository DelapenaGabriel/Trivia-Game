package com.trivia.dao;

import com.trivia.model.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getAllQuestions();

    List<Question> getQuestionsByCategoryId(int categoryId);

    Question getQuestionById(int id);

    Question updateQuestion(Question updateQuestion);

    Question addQuestions(Question newQuestion);

    int deleteQuestion(int id);

}
