package org.step.repository.pool;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.Optional;

public interface ConnectionPool {

    Connection getConnection();

    void releaseConnection(Connection connection);

    void commitTransaction(Connection connection);

    void rollbackTransaction(Connection connection);

    void rollbackTransactionWithSavepoint(Connection connection, Savepoint savepoint);

    Optional<Savepoint> setSavepoint(Connection connection, String savePointName);
}
