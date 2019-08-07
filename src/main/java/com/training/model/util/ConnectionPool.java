package com.training.model.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private final static String DB_DRIVER_CLASS_NAME = "driver.class.name";
    private final static String DB_URL = "url";
    private final static String DB_USER_NAME = "username";
    private final static String DB_PASSWORD = "password";
    private final static String MAX_TOTAL = "maxTotal";
    private final static String MAX_IDLE = "maxIdle";
    private final static String MAX_WAIT_MILLIS = "maxWaitMillis";
    private final static String TRANSACTION_ISOLATION = "transactionIsolation";

    private static Properties properties = new Properties();
    private static ClassLoader classLoader;
    private static InputStream inputStream;
    private static BasicDataSource dataSource;

    static {
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getResourceAsStream("database.properties");
            properties.load(inputStream);
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS_NAME));
            dataSource.setUrl(properties.getProperty(DB_URL));
            dataSource.setUsername(properties.getProperty(DB_USER_NAME));
            dataSource.setPassword(properties.getProperty(DB_PASSWORD));
            dataSource.setMaxTotal(Integer.valueOf(properties.getProperty(MAX_TOTAL)));
            dataSource.setMaxIdle(Integer.valueOf(properties.getProperty(MAX_IDLE)));
            dataSource.setMaxWaitMillis(Long.valueOf(properties.getProperty(MAX_WAIT_MILLIS)));
            dataSource.setDefaultTransactionIsolation(Integer.valueOf(properties.getProperty(TRANSACTION_ISOLATION)));
        } catch (IOException e) {
            LOGGER.error("Connection pull cannot boot");
        }
    }

    public static Connection getConnection(boolean autocomit){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(autocomit);
            return connection;
        }catch (SQLException e){
            LOGGER.error("Cannot get connection");
        }
        return connection;
    }

    public static void commitTransaction(Connection connection){

        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Cannot commit transaction " + e.getStackTrace());
        }
    }

    public static void closeConnection(Connection connection){
        if (connection != null){
            try{
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e){
                LOGGER.error("Cannot close connection " + e.getStackTrace());
            }
        }
    }

    public static void transactionRollback(Connection connection) {
        if (connection != null){
            try{
                connection.rollback();
            }catch (SQLException e){
                LOGGER.error("Cannot rollback transaction " + e.getStackTrace());
            }
        }
    }
}
