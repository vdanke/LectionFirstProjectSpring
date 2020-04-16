package org.step.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IdChecker {

    private static final String ID_CHECKER = "select user_id from users";
    private static final String USER_ID_FIELD = "user_id";
    private static final long ID_START = 0;

    public static Long getNextId(Connection connection) throws Exception {
        List<Long> idList = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(ID_CHECKER);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            idList.add(resultSet.getLong(USER_ID_FIELD));
        }

        long maxId = idList.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(ID_START);

        return ++maxId;
    }
}
