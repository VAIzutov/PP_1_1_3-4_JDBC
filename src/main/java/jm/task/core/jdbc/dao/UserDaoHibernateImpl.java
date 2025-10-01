package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    /**
     * Создание таблицы в БД
     */
    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String createTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(32) NOT NULL,
                        lastname VARCHAR(32) NOT NULL,
                        age INT NOT NULL
                    );
                    """;
            try {
                session.createNativeQuery(createTable).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Не удалось создать таблицу", e);
            }
        }
    }

    /**
     * Удаление таблицы в БД
     */
    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String dropTable = """
                    DROP TABLE IF EXISTS users;
                    """;
            try {
                session.createNativeQuery(dropTable).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Не удалось удалить таблицу", e);
            }
        }
    }

    /**
     * Добавление user-a в БД
     *
     * @param name     - имя
     * @param lastName - фамилия
     * @param age      - возраст
     */
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = new User(name, lastName, age);
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Не удалось добавить пользователя", e);
            }
        }
    }

    /**
     * Удаление user-a из БД
     *
     * @param id - ключ пользователя
     */
    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Не удалось удалить пользователя", e);
            }
        }
    }

    /**
     * Возвращает всех пользователей из БД
     *
     * @return - список пользователей
     */
    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                List<User> users = session.createQuery("from User", User.class).getResultList();
                transaction.commit();
                for (User user : users) System.out.println(user);
                return users;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Не удалось прочитать пользователей", e);
            }
        }
    }

    /**
     * Очистка таблицы в БД. Рука тянулась сделать sql запрос c TRUNCATE, но в задании это было запрещено
     */
    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                List<User> users = session.createQuery("from User", User.class).getResultList();
                for (User user : users) session.remove(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Не удалось очистить таблицу", e);
            }
        }
    }
}
