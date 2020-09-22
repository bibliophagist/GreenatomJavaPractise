package second.tasks.jdbc.hibernate.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import second.tasks.jdbc.hibernate.dao.connection.ConnectionManager;
import second.tasks.jdbc.hibernate.dao.user.User;
import second.tasks.jdbc.hibernate.dao.user.UserDAO;

import javax.persistence.criteria.*;
import java.sql.SQLException;
import java.util.List;

public class UserDAOHibernateCriteria implements UserDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    @Override
    public User findById(Integer id) throws SQLException {
        try (Session session = ConnectionManager.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            Query<User> query = session.createQuery(criteriaQuery);
            List<User> results = query.getResultList();
            User user = results.get(0);
            System.out.println(user.toString());
            return results.get(0);

            //TODO not working without sout
//            User user = session.load(User.class, id);
//            System.out.println(user.toString());
//            return user;
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
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<User> criteriaDelete = criteriaBuilder.createCriteriaDelete(User.class);
            Root<User> root = criteriaDelete.from(User.class);
            criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));
            session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public void update(Integer userToUpdateId, User newUser) throws SQLException {
        try (Session session = ConnectionManager.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
            Root<User> root = criteriaUpdate.from(User.class);
            criteriaUpdate.set("name", newUser.getName());
            criteriaUpdate.set("surname", newUser.getSurname());
            criteriaUpdate.set("nickname", newUser.getNickname());
            criteriaUpdate.set("password", newUser.getPassword());
            criteriaUpdate.set("registration_date", newUser.getRegistration_date());
            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), userToUpdateId));
            session.createQuery(criteriaUpdate).executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public List<User> getListOfUsers() throws SQLException {
        return null;
    }
}
