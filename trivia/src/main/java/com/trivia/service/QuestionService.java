package com.trivia.service;

import com.trivia.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestions();

    List<Question> getQuestionsByCategoryId(int categoryId);

    Question getQuestionById(int id);

    Question updateQuestion(Question updateQuestion);

    Question addQuestions(Question newQuestion);

    int deleteQuestion(int id);
}
