package database;

import java.sql.*;

public class DbConnectionWrapper {
    // Postgres DB details. DB resides in AWS-RDS
    private static final String JDBC_URL = "jdbc:postgresql://lib-wcc-pg.c1okx4rubqww.us-west-2.rds.amazonaws.com:5432/postgres";
    private static final String JDBC_USER = "wcclibadmin";
    private static final String JDBC_PASSWORD = "MEnMBX2ELpnWnSlIfUWy";
    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public int executeUpdate(String query, String... values) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        setStatementValues(statement, values);
        return statement.executeUpdate();
    }

    private void setStatementValues(PreparedStatement statement, String... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setString(i + 1, values[i]);
        }
    }

        // Other database-related methods can be added here

//        public static void main(String[] args) {
//            DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
//
//            try {
//                dbWrapper.connect();
//
//                // Execute queries or updates
//                ResultSet rs = dbWrapper.executeQuery("SELECT * FROM mytable");
//                // Process result set
//
//                int rowsAffected = dbWrapper.executeUpdate("INSERT INTO mytable(id, name) VALUES (1, 'John')");
//                // Process the result
//
//                dbWrapper.disconnect();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

}
