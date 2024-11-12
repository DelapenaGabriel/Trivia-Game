package com.trivia.dao;

import com.trivia.exception.DaoException;
import com.trivia.model.Category;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository

public class JdbcCategoryDao implements CategoryDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCategoryDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name " +
                "FROM category;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while(result.next()){
                categories.add(mapRowToCategory(result));
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return categories;
    }

    @Override
    public Category getCategoryByID(int id) {
        Category category = null;
        String sql = "SELECT id, name " +
                "FROM category " +
                "WHERE id = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
                if (result.next()){
                    category = mapRowToCategory(result);
                }

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return category;
    }

    @Override
    public Category updateCategory(Category updateCategory) {
        Category category = null;

        String sql = "UPDATE category " +
                "SET name = ? " +
                "WHERE id = ?;";

        try{
            int rowsAffected = jdbcTemplate.update(sql, updateCategory.getCategoryText(), updateCategory.getId());
            if (rowsAffected == 0){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            category = getCategoryByID(updateCategory.getId());
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return category;
    }

    @Override
    public Category addCategory(Category newCategory) {
        Category category = null;
        int newId;

        String sql = "INSERT INTO category (name) " +
                "VALUES (?) RETURNING id;";

        try{
            newId = jdbcTemplate.queryForObject(sql, int.class, newCategory.getCategoryText());
            category = getCategoryByID(newId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return category;
    }

    @Override
    public int deleteCategory(int id) {
        String sql = "DELETE FROM category WHERE id = ?;";
        int rowsDeleted = 0;

        try{
            rowsDeleted = jdbcTemplate.update(sql, id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowsDeleted;

    }

    private Category mapRowToCategory(SqlRowSet rowSet){
        Category category = new Category();
        category.setId(rowSet.getInt("id"));
        category.setCategoryText(rowSet.getString("name"));
        return category;
    }
}
