package second.tasks.jdbc.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import second.tasks.jdbc.hibernate.dao.hibernate.UserDAOHibernateCriteria;
import second.tasks.jdbc.hibernate.dao.hibernate.UserDAOHibernateHQL;
import second.tasks.jdbc.hibernate.dao.jdbc.UserDAOJDBC;
import second.tasks.jdbc.hibernate.dao.user.User;
import second.tasks.jdbc.hibernate.dao.user.UserDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOJDBC.class);

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAOHibernateHQL();
        User user = new User("2", "2", "2", "2", LocalDate.now());
        try {
            userDAO.update(2, user);
        } catch (SQLException sqlException) {
            LOGGER.error("Failed to get a User by id " + 1, sqlException);
        }
    }
}
