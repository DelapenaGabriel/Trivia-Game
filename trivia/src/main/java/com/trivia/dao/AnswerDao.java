package com.trivia.dao;

import com.trivia.model.Answer;

import java.util.List;

public interface AnswerDao {
    List<Answer> getAllAnswers();

    List<Answer> getAnswersByQuestionId(int questionId);

    Answer getAnswerById(int id);

    Answer updateAnswer(Answer updateAnswer);
    Answer addAnswer(Answer newAnswer);

    int deleteAnswer(int id);
}
