package com.trivia.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Answer {
    private int id;
    @NotBlank (message = "Question id must not be blank")
    private int questionId;
    @NotBlank (message = "Answer field must not be blank")
    private String answerText;
    @NotBlank (message = "Boolean correct field must not be blank")
    private boolean isCorrect;

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id && questionId == answer.questionId && isCorrect == answer.isCorrect && Objects.equals(answerText, answer.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionId, answerText, isCorrect);
    }

    @Override
    public String toString() {
        return "answer{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
