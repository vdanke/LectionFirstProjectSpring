package org.step.repository.impl;

import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.repository.pool.ConnectionPool;
import org.step.repository.pool.ConnectionPoolImpl;
import org.step.util.IdChecker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository<User> {

    private static final String REGISTRATION = "insert into users(user_id,username,password) values(?,?,?)";
    private static final String FIND_ALL = "select * from users";
    private static final String FIND_BY_ID = "select * from users where user_id=?";
    private static final String DELETE = "delete from users where user_id=?";

    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public boolean save(User user) {
        Connection connection = connectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REGISTRATION);
            preparedStatement.setLong(1, IdChecker.getNextId(connection));
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            int i = preparedStatement.executeUpdate();
            if (i != -1) {
                connectionPool.commitTransaction(connection);
            }
            return true;
        } catch (Exception e) {
            connectionPool.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        Connection connection = connectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, user.getId());
            int i = preparedStatement.executeUpdate();
            if (i != -1) {
                connectionPool.commitTransaction(connection);
            }
            return true;
        } catch (Exception e) {
            connectionPool.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
        Connection connection = connectionPool.getConnection();
        User user;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long user_id = resultSet.getLong(USER_ID);
                String username = resultSet.getString(USERNAME);
                String password = resultSet.getString(PASSWORD);
                user = new User(user_id, username, password);
                return Optional.of(user);
            }
        } catch (Exception e) {
            connectionPool.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        Connection connection = connectionPool.getConnection();
        List<User> userList = new ArrayList<>();
        Savepoint savepoint = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Optional<Savepoint> optionalSavepoint = connectionPool.setSavepoint(
                        connection, "savePoint" + resultSet.getLong(USER_ID)
                );
                if (optionalSavepoint.isPresent()) {
                    savepoint = optionalSavepoint.get();
                }
                userList.add(setUserFromDatabase(resultSet));
            }
        } catch (Exception e) {
            connectionPool.rollbackTransactionWithSavepoint(connection, savepoint);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return userList;
    }

    @Override
    public User update(User user) {
        return null;
    }

    private User setUserFromDatabase(ResultSet resultSet) throws Exception {
        User user = new User();

        user.setId(resultSet.getLong(USER_ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setPassword(resultSet.getString(PASSWORD));

        return user;
    }
}
