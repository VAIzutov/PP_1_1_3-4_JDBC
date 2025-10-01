package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mysql_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создание фабрики сессий
     *
     * @return - SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url",
                URL + "?useSSL=false&serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", USERNAME);
        configuration.setProperty("hibernate.connection.password", PASSWORD);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.show_sql", "true");

        configuration.addAnnotatedClass(User.class);

        return configuration.buildSessionFactory();
    }
}
