package com.trivia.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Category {
    private int id;
    @NotBlank(message = "Category Field must not be blank")
    private String categoryText;


    public Category(){}

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCategoryText(){
        return categoryText;
    }
    public void setCategoryText(String text){
        this.categoryText = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(categoryText, category.categoryText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryText);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryText='" + categoryText + '\'' +
                '}';
    }
}
