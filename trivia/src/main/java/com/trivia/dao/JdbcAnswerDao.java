package com.trivia.dao;

import com.trivia.exception.DaoException;
import com.trivia.model.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcAnswerDao implements AnswerDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAnswerDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT id, question_id, answer_text, is_correct " +
                "FROM answer;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while(result.next()){
                answers.add(mapRowToAnswer(result));
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        }
        return answers;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(int questionId) {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT id, question_id, answer_text, is_correct " +
                "FROM answer " +
                "WHERE question_id = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, questionId);
            while(result.next()){
                answers.add(mapRowToAnswer(result));
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        }
        return answers;
    }

    @Override
    public Answer getAnswerById(int id) {
        Answer answer = null;
        String sql = "SELECT id, question_id, answer_text, is_correct " +
                "FROM answer " +
                "WHERE id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()){
                answer = mapRowToAnswer(result);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        }
        return answer;
    }

    @Override
    public Answer updateAnswer(Answer updateAnswer) {
        Answer answer = null;

        String sql = "UPDATE FROM answer " +
                "SET question_id = ?, answer_text = ?, is_correct = ? " +
                "WHERE id = ?;";

        try{
            int rowsAffected = jdbcTemplate.update(sql, updateAnswer.getQuestionId(), updateAnswer.getAnswerText(), updateAnswer.isCorrect(), updateAnswer.getId());
            if (rowsAffected == 0){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            answer = getAnswerById(updateAnswer.getId());
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return answer;
    }

    @Override
    public Answer addAnswer(Answer newAnswer) {
        Answer answer = null;
        int newId;

        String sql = "INSERT INTO answer (question_id, answer_text, is_correct) " +
                "VALUES (?,?,?) RETURNING id;";

        try{
            newId = jdbcTemplate.queryForObject(sql, int.class, newAnswer.getQuestionId(), newAnswer.getAnswerText(), newAnswer.isCorrect());
            answer = getAnswerById(newId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return answer;
    }

    @Override
    public int deleteAnswer(int id) {
        String sql = "DELETE FROM answer WHERE id = ?;";

        try{
            return jdbcTemplate.update(sql, id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Answer mapRowToAnswer(SqlRowSet rowSet){
        Answer answer = new Answer();
        answer.setId(rowSet.getInt("id"));
        answer.setQuestionId(rowSet.getInt("question_id"));
        answer.setAnswerText(rowSet.getString("answer_text"));
        answer.setCorrect(rowSet.getBoolean("is_correct"));
        return answer;
    }
}
