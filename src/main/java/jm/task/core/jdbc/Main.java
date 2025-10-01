package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userDaoService = new UserServiceImpl();

        userDaoService.createUsersTable();

        userDaoService.saveUser("Name1", "LastName1", (byte) 20);
        userDaoService.saveUser("Name2", "LastName2", (byte) 25);
        userDaoService.saveUser("Name3", "LastName3", (byte) 31);
        userDaoService.saveUser("Name4", "LastName4", (byte) 38);

        userDaoService.removeUserById(1);
        userDaoService.getAllUsers();
        userDaoService.cleanUsersTable();
        userDaoService.dropUsersTable();

        Util.getSessionFactory().close();
    }
}
