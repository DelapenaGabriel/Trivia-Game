package com.trivia.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Question {
    private int id;
    @NotBlank
    private int categoryId;
    @NotBlank
    private String questionText;

    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id && categoryId == question.categoryId && Objects.equals(questionText, question.questionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, questionText);
    }

    @Override
    public String toString() {
        return "question{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}
