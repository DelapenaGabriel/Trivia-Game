package com.trivia.dao;

import com.trivia.exception.DaoException;
import com.trivia.model.Question;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcQuestionDao implements QuestionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcQuestionDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        String sql = "SELECT id, category_id, question_text " +
                "FROM question;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while (result.next()){
                questions.add(mapRowToQuestion(result));
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return questions;
    }

    @Override
    public List<Question> getQuestionsByCategoryId(int categoryId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT id, category_id, question_text " +
                "FROM question " +
                "WHERE category_id = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, categoryId);
            while(result.next()){
                questions.add(mapRowToQuestion(result));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return questions;
    }

    @Override
    public Question getQuestionById(int id) {
        Question question = null;

        String sql = "SELECT id, category_id, question_text FROM question " +
                "WHERE id = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()){
                question = mapRowToQuestion(result);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return question;
    }

    @Override
    public Question updateQuestion(Question updateQuestion) {
        Question question = null;

        String sql = "UPDATE question " +
                "SET category_id = ?, question_text = ? " +
                "WHERE id = ?";

        try{
            int rowsAffected = jdbcTemplate.update(sql, updateQuestion.getCategoryId(), updateQuestion.getQuestionText(), updateQuestion.getId());
            if (rowsAffected == 0){
                throw new DaoException("Zero rows affected, expected at least one");
            }

            question = getQuestionById(updateQuestion.getId());
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return question;
    }

    @Override
    public Question addQuestions(Question newQuestion) {
        Question question = null;
        int newId;

        String sql = "INSERT INTO question (category_id, question_text) " +
                "VALUES (?,?) RETURNING id;";

        try{
            newId = jdbcTemplate.queryForObject(sql, int.class, newQuestion.getCategoryId(), newQuestion.getQuestionText());
            question = getQuestionById(newId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } return question;
    }

    @Override
    public int deleteQuestion(int id) {
        String sqlAnswer = "DELETE FROM answer WHERE question_id = ?;";
        String sql = "DELETE FROM question WHERE id = ?;";

        try{
            jdbcTemplate.update(sqlAnswer, id);
            return jdbcTemplate.update(sql, id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Question mapRowToQuestion(SqlRowSet rowSet){
        Question question = new Question();
        question.setId(rowSet.getInt("id"));
        question.setCategoryId(rowSet.getInt("category_id"));
        question.setQuestionText(rowSet.getString("question_text"));
        return question;
    }
}
