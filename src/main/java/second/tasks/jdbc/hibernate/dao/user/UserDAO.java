package second.tasks.jdbc.hibernate.dao.user;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    User findById(Integer id) throws SQLException;

    void save(User user) throws SQLException;

    void delete(Integer id) throws SQLException;

    void update(Integer userToUpdateId, User newUser) throws SQLException;

    List<User> getListOfUsers() throws SQLException;

}
