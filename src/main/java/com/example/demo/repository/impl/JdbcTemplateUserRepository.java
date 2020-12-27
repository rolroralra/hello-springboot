package com.example.demo.repository.impl;

import com.example.demo.model.User;
import com.example.demo.repository.Tables;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

// SpringJDBC
@Repository("JdbcTemplateUserRepository")
@Slf4j
public class JdbcTemplateUserRepository implements UserRepository {
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcTemplateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return jdbcTemplate.query("SELECT * FROM SPRING_USER", new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public User getUserByUserId(int userId) throws SQLException {
        return jdbcTemplate.queryForObject("SELECT * FROM SPRING_USER WHERE USER_ID = ?", (rs, rowNum) -> {
            User user = new User(
                    rs.getInt(Tables.USER_TABLE.getColumn(0)),
                    rs.getString(Tables.USER_TABLE.getColumn(1))
            );

            return user;
        }, userId);
    }

    @Override
    public User insertUser(User user) throws SQLException {
        int result = jdbcTemplate.update("INSERT INTO SPRING_USER(USER_ID, USER_NAME) VALUES(?, ?)", user.getUserId(), user.getUserName());

        if (result > 0) {
            return user;
        }
        else {
            throw new SQLException("Already Exists User. Failed to INSERT");
        }
    }

    @Override
    public void updateUser(int userId, User user) throws SQLException {
        int result = jdbcTemplate.update("UPDATE SPRING_USER SET USER_NAME = ? WHERE USER_ID = ?", user.getUserName(), user.getUserId());

        if (result <= 0) {
            throw new SQLException("Not Exits User. Failed to UPDATE");
        }
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        int result = jdbcTemplate.update("DELETE FROM SPRING_USER WHERE USER_ID = ?", userId);

        if (result <= 0) {
            throw new SQLException("Not Exits User. Failed to DELETE");
        }
    }
}
