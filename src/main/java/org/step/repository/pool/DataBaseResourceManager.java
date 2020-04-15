package org.step.repository.pool;

import java.util.ResourceBundle;

public class DataBaseResourceManager {

    private static volatile DataBaseResourceManager instance = new DataBaseResourceManager();

    private final ResourceBundle dataBaseBundle = ResourceBundle.getBundle("db");

    public static DataBaseResourceManager getInstance() {
        DataBaseResourceManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPoolImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataBaseResourceManager();
                }
            }
        }
        return localInstance;
    }

    String getDataBaseBundleValueByKey(String key) {
        return dataBaseBundle.getString(key);
    }
}
