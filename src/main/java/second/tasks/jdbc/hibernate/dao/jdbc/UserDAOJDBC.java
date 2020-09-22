package second.tasks.jdbc.hibernate.dao.jdbc;

import second.tasks.jdbc.hibernate.dao.connection.ConnectionManager;
import second.tasks.jdbc.hibernate.dao.user.User;
import second.tasks.jdbc.hibernate.dao.user.UserDAO;

import java.sql.*;
import java.util.List;

public class UserDAOJDBC implements UserDAO {


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
    public List<User> getListOfUsers() {

        return null;
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
