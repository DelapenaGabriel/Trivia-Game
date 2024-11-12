package com.trivia.service;

import com.trivia.dao.CategoryDao;
import com.trivia.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestCategoryService implements CategoryService{
    private CategoryDao categoryDao;

    public RestCategoryService(CategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Category getCategoryByID(int id) {
        return categoryDao.getCategoryByID(id);
    }

    @Override
    public Category updateCategory(Category updateCategory) {
        return categoryDao.updateCategory(updateCategory);
    }

    @Override
    public Category addCategory(Category newCategory) {
        return categoryDao.addCategory(newCategory);
    }

    @Override
    public int deleteCategory(int id) {
        return categoryDao.deleteCategory(id);
    }
}
