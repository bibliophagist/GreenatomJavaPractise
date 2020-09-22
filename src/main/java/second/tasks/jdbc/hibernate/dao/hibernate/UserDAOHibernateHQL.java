package second.tasks.jdbc.hibernate.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import second.tasks.jdbc.hibernate.dao.connection.ConnectionManager;
import second.tasks.jdbc.hibernate.dao.user.User;
import second.tasks.jdbc.hibernate.dao.user.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class UserDAOHibernateHQL implements UserDAO {

    @Override
    public User findById(Integer id) throws SQLException {
        try (Session session = ConnectionManager.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User where id= :id", User.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    @Override
    public void save(User user) throws SQLException {
        try (Session session = ConnectionManager.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (Session session = ConnectionManager.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("delete from User where id= :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public void update(Integer userToUpdateId, User newUser) throws SQLException {
        try (Session session = ConnectionManager.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("update User set name = :name, surname = :surname, " +
                    " password = :password, nickname = :nickname, registration_date = :registration_date " +
                    "where id= :id");
            query.setParameter("name", newUser.getName());
            query.setParameter("surname", newUser.getSurname());
            query.setParameter("nickname", newUser.getNickname());
            query.setParameter("password", newUser.getPassword());
            query.setParameter("registration_date", newUser.getRegistration_date());
            query.setParameter("id", userToUpdateId);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public List<User> getListOfUsers() throws SQLException {
        return null;
    }
}
