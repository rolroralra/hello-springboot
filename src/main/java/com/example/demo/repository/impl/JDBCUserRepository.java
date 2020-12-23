package com.example.demo.repository.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.repository.Tables.USER_TABLE;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

@Repository(value = "JDBCUserRepository")
@Slf4j
public class JDBCUserRepository implements UserRepository {
    private DataSource dataSource;

    @Autowired
    public JDBCUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void closeConnection(Connection conn, PreparedStatement pstm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rollback(Connection conn) throws SQLException {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            log.info("{} {}", conn, pstm);

            // PreparedStatement can't use tableName variable. (in JDBC)
            pstm = conn.prepareStatement("SELECT * FROM SPRING_USER");
//            pstm.setString(1, USER_TABLE.getTableName());
            rs = pstm.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(USER_TABLE.getColumn(0));
                String userName = rs.getString(USER_TABLE.getColumn(1));
                User user = new User(userId, userName);
                users.add(user);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            closeConnection(conn, pstm, rs);
        }

        return users;
    }

    @Override
    public User getUserByUserId(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        User user = new User(0, "Not Exists User");
        try {
            conn = dataSource.getConnection();
            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            pstm = conn.prepareStatement("SELECT * FROM SPRING_USER WHERE USER_ID = ?");
            pstm.setInt(1, userId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int userId2 = rs.getInt(USER_TABLE.getColumn(0));
                String userName2 = rs.getString(USER_TABLE.getColumn(1));
                user = new User(userId2, userName2);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            closeConnection(conn, pstm, rs);
        }

        return user;
    }

    @Override
    public User insertUser(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            pstm = conn.prepareStatement("INSERT INTO SPRING_USER VALUES(?, ?)");
            pstm.setInt(1, user.getUserId());
            pstm.setString(2, user.getUserName());
            if (pstm.executeUpdate() > 0) {
                conn.commit();
            }
            else {
                throw new SQLException("Insert Failed");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            closeConnection(conn, pstm, rs);
        }

        return user;
    }

    @Override
    public void updateUser(int userId, User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            pstm = conn.prepareStatement("UPDATE SPRING_USER SET USER_NAME = ? WHERE USER_ID = ?");
            pstm.setString(1, user.getUserName());
            pstm.setInt(2, user.getUserId());
            if (pstm.executeUpdate() > 0) {
                conn.commit();
            }
            else {
                throw new SQLException("Update Failed");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            closeConnection(conn, pstm, rs);
        }

    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            pstm = conn.prepareStatement("DELETE FROM SPRING_USER WHERE USER_ID = ?");
            pstm.setInt(1, userId);
            if (pstm.executeUpdate() > 0) {
                conn.commit();
            }
            else {
                throw new SQLException("Delete Failed");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            rollback(conn);
            throw e;
        } finally {
            closeConnection(conn, pstm, rs);
        }

    }
}
