package com.trivia.service;

import com.trivia.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryByID(int id);

    Category updateCategory(Category updateCategory);

    Category addCategory(Category newCategory);

    int deleteCategory(int id);
}
