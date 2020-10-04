package second.tasks.jdbc.hibernate.dao.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import second.tasks.jdbc.hibernate.dao.user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "5264552";
    private static SessionFactory sessionFactory;
    private final Connection connection;

    public ConnectionManager() {
        this.connection = setConnection(USER, PASS);
    }

    public ConnectionManager(String user, String pass) {
        this.connection = setConnection(user, pass);
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection setConnection(String user, String pass) {
        try {
            return DriverManager.getConnection(URL,
                    user, pass);
        } catch (SQLException sqlException) {
            LOGGER.error("Failed to get a connection to the database", sqlException);
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Map<String, String> settings = new HashMap<>();
            settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            settings.put("hibernate.connection.url", URL);
            settings.put("hibernate.connection.username", USER);
            settings.put("hibernate.connection.password", PASS);
            settings.put("hibernate.show_sql", "true");
            settings.put("hibernate.hbm2ddl.auto", "update");
            settings.put("hibernate.default_schema", "jdbc_hibernate");

            ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(settings).build();
            MetadataSources sources = new MetadataSources(registry)
                    .addAnnotatedClass(User.class);
            sessionFactory = sources.buildMetadata().buildSessionFactory();

        }
        return sessionFactory;
    }
}
