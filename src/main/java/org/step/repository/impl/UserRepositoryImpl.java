package org.step.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.repository.pool.ConnectionPool;
import org.step.repository.pool.ConnectionPoolImpl;
import org.step.util.IdChecker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository<User> {

    private static final String REGISTRATION = "insert into users(user_id,username,password) values(?,?,?)";
    private static final String FIND_ALL = "select * from users";
    private static final String FIND_BY_ID = "select * from users where user_id=?";
    private static final String DELETE = "delete from users where user_id=?";
    private static final String UPDATE = "update users set username=?, password=? where user_id=?";
    private static final String UPDATE_ROLE = "insert into authorities(user_id,authorities) values(?,?)";
    private static final String LOGIN = "select * from users where username=?";
    private static final String FIND_AUTHORITIES_BY_ID = "select * from authorities where user_id = ?";

    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private final IdChecker idChecker;

    @Autowired
    public UserRepositoryImpl(IdChecker idChecker) {
        this.idChecker = idChecker;
    }

    @Override
    public Optional<User> login(User user) {
        Connection connection = connectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN);
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getLong("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connectionPool.rollbackTransaction(connection);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        Connection connection = connectionPool.getConnection();
        Long nextId;

        try {
            nextId = idChecker.getNextId();

            PreparedStatement preparedStatement = connection.prepareStatement(REGISTRATION);
            preparedStatement.setLong(1, nextId);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            int i = preparedStatement.executeUpdate();
            if (i != -1) {
                connectionPool.commitTransaction(connection);
            }
            user.setId(nextId);

            return user;
        } catch (Exception e) {
            connectionPool.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return user;
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
        Connection connection = connectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());
            int i = preparedStatement.executeUpdate();
            if (i != -1) {
                connectionPool.commitTransaction(connection);
            }
            return user;
        } catch (Exception e) {
            connectionPool.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public boolean saveAuthorities(User user) {
        Connection connection = connectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getRole().name());
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
    public Optional<String> findAuthoritiesByUserId(Long id) {
        Connection connection = connectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHORITIES_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(resultSet.getString("authorities"));
            }
        } catch (Exception e) {
            connectionPool.rollbackTransaction(connection);
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return Optional.empty();
    }

    private User setUserFromDatabase(ResultSet resultSet) throws Exception {
        User user = new User();

        user.setId(resultSet.getLong(USER_ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setPassword(resultSet.getString(PASSWORD));

        return user;
    }
}
