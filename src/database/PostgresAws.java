package database;

import java.sql.*;

public class PostgresAws {
    private static final String JDBC_URL = "jdbc:postgresql://lib-wcc-pg.c1okx4rubqww.us-west-2.rds.amazonaws.com:5432/postgres";
    private static final String JDBC_USER = "wcclibadmin";
    private static final String JDBC_PASSWORD = "MEnMBX2ELpnWnSlIfUWy";

    public static void main(String[] args) {
        try {
            // Step 1: Establish a connection to the database
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Step 2: Create a statement object
            Statement stmt = conn.createStatement();

            // Step 3: Execute a query to read data
            ResultSet rs = stmt.executeQuery("SELECT * FROM mytable");

            // Step 4: Process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // Step 5: Execute a query to insert data
            stmt.executeUpdate("INSERT INTO mytable(id, name) VALUES (1, 'John')");

            // Step 6: Clean up resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

