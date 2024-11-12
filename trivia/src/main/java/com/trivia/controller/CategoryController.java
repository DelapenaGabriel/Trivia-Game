package com.trivia.controller;

import com.trivia.exception.DaoException;
import com.trivia.model.Category;
import com.trivia.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/category")
@RestController
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController (CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public List<Category> listCategories(){
        List<Category> categories;

        try{
            categories = categoryService.getAllCategories();
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return categories;
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable int id){
        Category category;
        try{
            category = categoryService.getCategoryByID(id);
            if (category == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found");
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return category;
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable int id, @Valid @RequestBody Category updateCategory){
        updateCategory.setId(id);

        try{
            Category updatedCategory = categoryService.updateCategory(updateCategory);
            return updatedCategory;
        }catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"); //Status Code: 404 = The given URL doesn't point to a valid resource
        }
    }

    @PostMapping("")
    public Category add(@Valid @RequestBody Category newCategory){
        Category category = null;

        try{
            category = categoryService.addCategory(newCategory);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
        return category;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        try{
            int deletedRows = categoryService.deleteCategory(id);
            if (deletedRows == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"); //Status Code: 404 = The given URL doesn't point to a valid resource
            }
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()); //Status Code: 500 = API itself has a problem and can't fulfill the request at this time
        }
    }

}
