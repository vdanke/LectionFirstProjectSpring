package org.step.repository.pool;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectionPoolImpl implements ConnectionPool {

    private static volatile ConnectionPoolImpl instance = new ConnectionPoolImpl();

    private BlockingQueue<Connection> connectionsQueue;
    private Connection connection;

    private final int poolSize = 5;

    private ConnectionPoolImpl() {
    }

    public static ConnectionPool getInstance() {
        ConnectionPoolImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPoolImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPoolImpl();
                }
            }
        }
        localInstance.init();
        return localInstance;
    }

    @Override
    public Connection getConnection() {
        Connection newConnection;

        if (connectionsQueue.isEmpty() || connectionsQueue.size() < poolSize) {
            for (int i = 0; i < poolSize - connectionsQueue.size(); i++) {
                connectionsQueue.add(connection);
            }
        }

        try {
            newConnection = connectionsQueue.take();
        } catch (InterruptedException e) {
            newConnection = connectionsQueue.poll();
        }
        return newConnection;
    }

    @Override
    public void releaseConnection(Connection connection) {

        try {
            connectionsQueue.put(connection);
        } catch (InterruptedException e) {
            if (connectionsQueue.size() >= poolSize) {
                connectionsQueue.remove();
            }
            connectionsQueue.offer(connection);
        }
    }

    @Override
    public Optional<Savepoint> setSavepoint(Connection connection, String savePointName) {
        try {
            if (connection != null && !savePointName.isEmpty()) {
                return Optional.ofNullable(connection.setSavepoint(savePointName));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void commitTransaction(Connection connection) {
        try {
            checkConnectionIsNotNull(connection);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollbackTransaction(Connection connection) {
        try {
            checkConnectionIsNotNull(connection);
            connection.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollbackTransactionWithSavepoint(Connection connection, Savepoint savepoint) {
        try {
            checkConnectionIsNotNull(connection);
            connection.rollback(savepoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        DataBaseResourceManager dataBaseResourceManager = DataBaseResourceManager.getInstance();

        String url = dataBaseResourceManager.getDataBaseBundleValueByKey(DataBaseProperties.DATA_BASE_URL);
        String user = dataBaseResourceManager.getDataBaseBundleValueByKey(DataBaseProperties.DATA_BASE_USER);
        String password = dataBaseResourceManager.getDataBaseBundleValueByKey(DataBaseProperties.DATA_BASE_PASSWORD);
        String driver = dataBaseResourceManager.getDataBaseBundleValueByKey(DataBaseProperties.DATA_BASE_DRIVER);

        try {
            if (!url.contains("memory")) {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } else {
                connection = DriverManager.getConnection(url);
                Stream<String> lines = Files.lines(Paths.get("src/main/resources/db.creator.sql"));
                String collect = lines.collect(Collectors.joining());
                PreparedStatement preparedStatement = connection.prepareStatement(collect);
                preparedStatement.executeUpdate();
            }
            connectionsQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                connection.setAutoCommit(false);
                connectionsQueue.add(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkConnectionIsNotNull(Connection connection) throws Exception {
        if (connection == null) {
            throw new Exception("Connection is closed");
        }
    }
}