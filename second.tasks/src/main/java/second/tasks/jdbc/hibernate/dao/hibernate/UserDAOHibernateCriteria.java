package second.tasks.jdbc.hibernate.dao.hibernate;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import second.tasks.jdbc.hibernate.dao.connection.ConnectionManager;
import second.tasks.jdbc.hibernate.dao.user.User;
import second.tasks.jdbc.hibernate.dao.user.UserDAO;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDAOHibernateCriteria implements UserDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    private final static int PAGE_SIZE = 10;

    @Override
    public User findById(Integer id) throws SQLException {
        try (Session session = ConnectionManager.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            Query<User> query = session.createQuery(criteriaQuery);
            List<User> results = query.getResultList();
            return results.get(0);
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
    public Queue<User> getUserQueue(User user) throws SQLException {
        Queue<User> users = new ConcurrentLinkedQueue<>();
        Thread myThready = new Thread(() -> {
            try (Session session = ConnectionManager.getSessionFactory().openSession()) {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<Long> countQuery = criteriaBuilder
                        .createQuery(Long.class);
                countQuery.select(criteriaBuilder
                        .count(countQuery.from(User.class)));
                Long count = session.createQuery(countQuery)
                        .getSingleResult();
                if (user.getName() != null) {
                    session.enableFilter("userNameFilter").setParameter("name", user.getName());
                }
                if (user.getSurname() != null) {
                    session.enableFilter("userSurnameFilter").setParameter("surname", user.getSurname());
                }
                if (user.getNickname() != null) {
                    session.enableFilter("userNicknameFilter").setParameter("nickname", user.getNickname());
                }
                if (user.getPassword() != null) {
                    session.enableFilter("userPasswordFilter").setParameter("password", user.getPassword());
                }

                CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
                Root<User> from = criteriaQuery.from(User.class);
                CriteriaQuery<User> select = criteriaQuery.select(from);

                TypedQuery<User> typedQuery = session.createQuery(select);
                int rowNumber = 1;
                while (rowNumber < count.intValue()) {
                    typedQuery.setFirstResult(rowNumber - 1);
                    typedQuery.setMaxResults(PAGE_SIZE);
                    System.out.println("rows added to queue");
                    users.addAll(typedQuery.getResultList());
                    rowNumber += PAGE_SIZE;
                }
            }
        });
        myThready.start();
        return users;
    }
}
