package com.sustainability;

import java.sql.Connection;
import java.sql.DriverManager;

public class ManageDatabase implements DOADatebaseManager{
    private static final String URL = "jdbc:sqlserver://178.128.246.22:1433;databaseName=sustainability;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "D24admin";
    private static Connection conn;

    /**
     * Establishes connection to the Database.
     * @return return the connection to the Database.
     */
    @Override
    public Connection getConnection() {
        if (conn == null) {
            try
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connected to the database.");
            }
            catch (Exception e)
            {
                System.out.println("Failed to connect to database.");
            }
        }
        return conn;
    }

    @Override
    public void addMeasesurementToDatebase() {

    }
}
