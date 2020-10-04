package second.tasks.jdbc.hibernate.dao.jdbc;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import second.tasks.jdbc.hibernate.dao.connection.ConnectionManager;
import second.tasks.jdbc.hibernate.dao.user.User;
import second.tasks.jdbc.hibernate.dao.user.UserDAO;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserDAOJDBC implements UserDAO {
    private final static int PAGE_SIZE = 10;
    private final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    @Override
    public User findById(Integer id) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        try (Connection connection = connectionManager.getConnection()) {
            String query = "SELECT * FROM jdbc_hibernate.users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return getUserFromResultSet(resultSet);
        }
    }

    @Override
    public void save(User user) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        String query = "INSERT INTO jdbc_hibernate.users (name,surname,nickname,password,registration_date)" +
                " VALUES (?,?,?,?,?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setDate(5, Date.valueOf(user.getRegistration_date()));
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        String query = "DELETE FROM jdbc_hibernate.users WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }
    }

    @Override
    public void update(Integer userToUpdateId, User newUser) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        String query = "UPDATE jdbc_hibernate.users " +
                "SET name = ? surname = ?  nickname = ?  password = ? registration_date = ? " +
                "WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newUser.getName());
            preparedStatement.setString(2, newUser.getSurname());
            preparedStatement.setString(3, newUser.getSurname());
            preparedStatement.setString(4, newUser.getPassword());
            preparedStatement.setDate(5, Date.valueOf(newUser.getRegistration_date()));
            preparedStatement.setInt(6, userToUpdateId);
            preparedStatement.execute();
        }
    }

    @Override
    public Queue<User> getUserQueue(User filter) throws SQLException {
        Queue<User> users = new ConcurrentLinkedQueue<>();
        Thread myThready = new Thread(() -> {
            ConnectionManager connectionManager = new ConnectionManager();
            try (Connection connection = connectionManager.getConnection();
                 Statement countStatement = connection.createStatement();
                 Statement statement = connection.createStatement();) {
                String SQL_SUBLIST = getQuery(filter);
                ResultSet rs = countStatement.executeQuery("select count(*) from jdbc_hibernate.users");
                rs.next();
                int count = rs.getInt(1);
                int rowNumber = 0;
                while (rowNumber < count) {
                    String sql = String.format(SQL_SUBLIST, PAGE_SIZE, rowNumber);
                    ResultSet resultSet = statement.executeQuery(sql);
                    List<User> userList = new ArrayList<>();
                    while (resultSet.next()) {
                        userList.add(getUserFromResultSet(resultSet));
                    }
                    System.out.println("10 rows added to queue");
                    users.addAll(userList);
                    rowNumber += PAGE_SIZE;
                }
            } catch (SQLException e) {
                LOGGER.error("Exception while getting the queue of users", e);
            }
        });
        myThready.start();
        return users;
    }

    private String getQuery(User filter) {
        return "SELECT id, " +
                "name as name," +
                "surname as surname," +
                "nickname as nickname," +
                "password as password ," +
                "registration_date as registration_date " +
                "FROM jdbc_hibernate.users " +
                "WHERE " +
                "(" + filter.getName() + " IS NULL OR name = '" + filter.getName() + "' ) AND" +
                "(" + filter.getNickname() + " IS NULL or nickname = '" + filter.getNickname() + "'  ) AND" +
                "(" + filter.getSurname() + " IS NULL or surname = '" + filter.getSurname() + "') AND" +
                "(" + filter.getPassword() + "  IS NULL or password = '" + filter.getPassword() + "')" +
                "ORDER BY id LIMIT %d OFFSET %d";
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setNickname(resultSet.getString("nickname"));
        user.setPassword(resultSet.getString("password"));
        user.setRegistration_date(resultSet.getDate("registration_date").toLocalDate());
        return user;
    }
}
