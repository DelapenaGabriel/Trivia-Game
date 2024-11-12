package com.trivia.dao;

import com.trivia.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories();

    Category getCategoryByID(int id);

    Category updateCategory(Category updateCategory);

    Category addCategory(Category newCategory);

    int deleteCategory(int id);
}
