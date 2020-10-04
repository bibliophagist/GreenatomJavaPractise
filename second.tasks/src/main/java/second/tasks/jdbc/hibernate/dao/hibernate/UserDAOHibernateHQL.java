package second.tasks.jdbc.hibernate.dao.hibernate;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import second.tasks.jdbc.hibernate.dao.connection.ConnectionManager;
import second.tasks.jdbc.hibernate.dao.user.User;
import second.tasks.jdbc.hibernate.dao.user.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserDAOHibernateHQL implements UserDAO {

    private final static int PAGE_SIZE = 10;

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
    public Queue<User> getUserQueue(User user) throws SQLException {
        Queue<User> users = new ConcurrentLinkedQueue<>();
        Thread myThready = new Thread(() -> {
            try (Session session = ConnectionManager.getSessionFactory().openSession()) {
                String hql = "FROM User user order by user.id";
                Query query = session.createQuery(hql);

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

                ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_SENSITIVE);
                resultScroll.first();
                resultScroll.scroll(0);
                boolean allEntitiesWasProcessed = false;
                while (!allEntitiesWasProcessed) {
                    int i = 0;
                    List<User> userList = new ArrayList<>();
                    while (PAGE_SIZE > i++) {
                        userList.add((User) resultScroll.get(0));
                        if (!resultScroll.next()) {
                            allEntitiesWasProcessed = true;
                            break;
                        }
                    }
                    System.out.println("rows added to queue");
                    users.addAll(userList);
                }
            }
        });
        myThready.start();
        return users;
    }
}
