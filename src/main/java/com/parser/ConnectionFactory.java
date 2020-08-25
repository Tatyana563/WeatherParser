package com.parser;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
//spring.jpa.hibernate.ddl-auto=update
//spring.datasource.url=jdbc:mysql://localhost:3306/weather?serverTimezone=UTC
//spring.datasource.username=root
//spring.datasource.password=apple25
public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/weather?serverTimezone=UTC";
    private static final String USERNMAME = "root";
    private static final String PASSWORD = "apple25";
   // private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver.";

    public static final BasicDataSource DATA_SOURCE = new BasicDataSource();

    static {
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNMAME);
        DATA_SOURCE.setPassword(PASSWORD);
      //  DATA_SOURCE.setDriverClassName(DRIVER_CLASS_NAME);
        DATA_SOURCE.setMinIdle(2);
        DATA_SOURCE.setMinIdle(10);
    }
    //Double checked locking
    private static volatile Connection connection;

    public static Connection getConnection() {
        if (Objects.isNull(connection)) {
            synchronized (ConnectionFactory.class) {
                if (connection == null) {
                    try {
                        connection = DATA_SOURCE.getConnection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }
}
