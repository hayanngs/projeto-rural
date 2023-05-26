package dev.hayann.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static final ConnectionPool instance = new ConnectionPool();

    private final static String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";

    private final static String JDBC_USERNAME = "postgres";

    private final static String JDBC_PASSWORD = "postgres";

    private static int POOL_SIZE = 4;

    private List<Connection> connectionsPool = new ArrayList<>();

    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = createConnection();
                connectionsPool.add(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() {
        Connection connection;
        if (connectionsPool.isEmpty()) {
            connection = createConnection();
            connectionsPool.add(connection);
            POOL_SIZE++;
        } else {
            return connectionsPool.remove(connectionsPool.size() - 1);
        }
        return null;
    }

    public synchronized void releaseConnection(Connection connection) {
        connectionsPool.add(connection);
    }

    public synchronized void closeAllConnections() {
        for (Connection connection : connectionsPool) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connectionsPool.clear();
    }
}
