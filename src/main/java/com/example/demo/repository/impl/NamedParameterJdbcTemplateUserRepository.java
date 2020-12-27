package com.example.demo.repository.impl;

import com.example.demo.model.User;
import com.example.demo.repository.Tables;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

// SpringJDBC
@Repository("NamedParameterJdbcTemplateUserRepository")
@Slf4j
public class NamedParameterJdbcTemplateUserRepository implements UserRepository {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public NamedParameterJdbcTemplateUserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return namedParameterJdbcTemplate.query("SELECT * FROM SPRING_USER", new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public User getUserByUserId(int userId) throws SQLException {
        return namedParameterJdbcTemplate.queryForObject(
                "SELECT * FROM SPRING_USER WHERE USER_ID = :USER_ID",
                new MapSqlParameterSource().addValue("USER_ID", userId),
                new BeanPropertyRowMapper<User>(User.class)
        );
    }

    @Override
    public User insertUser(User user) throws SQLException {
        int result = namedParameterJdbcTemplate.update(
                "INSERT INTO SPRING_USER(USER_ID, USER_NAME) VALUES(:userId, :userName)",
                new BeanPropertySqlParameterSource(user)
        );


        if (result > 0) {
            return user;
        }
        else {
            throw new SQLException("Already Exists User. Failed to INSERT");
        }
    }

    @Override
    public void updateUser(int userId, User user) throws SQLException {
        user.setUserId(userId);
        int result = namedParameterJdbcTemplate.update(
                "UPDATE SPRING_USER SET USER_NAME = :userName WHERE USER_ID = :userId",
                new BeanPropertySqlParameterSource(user)
        );

        if (result <= 0) {
            throw new SQLException("Not Exits User. Failed to UPDATE");
        }
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        int result = namedParameterJdbcTemplate.update(
                "DELETE FROM SPRING_USER WHERE USER_ID = :USER_ID",
                new MapSqlParameterSource().addValue(Tables.USER_TABLE.getIndexColumn(), userId)
        );

        if (result <= 0) {
            throw new SQLException("Not Exits User. Failed to DELETE");
        }
    }
}
