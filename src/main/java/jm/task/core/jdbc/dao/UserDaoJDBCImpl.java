package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    /**
     * Создание таблицы в БД
     */
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String createTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(32) NOT NULL,
                        lastname VARCHAR(32) NOT NULL,
                        age INT NOT NULL
                    );
                    """;
            statement.execute(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление таблицы в БД
     */
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String dropTable = """
                    DROP TABLE IF EXISTS users;
                    """;
            statement.execute(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавление user-a в БД
     *
     * @param name     - имя
     * @param lastName - фамилия
     * @param age      - возраст
     */
    public void saveUser(String name, String lastName, byte age) {
        String querySQL = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление user-a из БД
     *
     * @param id - ключ пользователя
     */
    public void removeUserById(long id) {
        String deleteById = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает всех пользователей из БД
     *
     * @return - список пользователей
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String getAll = """
                    SELECT * FROM users;
                    """;
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));

                System.out.println(user);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Очистка таблицы в БД
     */
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String cleanTable = """
                    TRUNCATE TABLE users;
                    """;
            statement.execute(cleanTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
