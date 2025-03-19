package com.sustainability;

import java.sql.Connection;
import java.sql.DriverManager;

public class ManageDatabase implements DOADatebaseManager{
    private static final String URL = "jdbc:sqlserver://178.128.246.22:1433;databaseName=sustainability;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "D24admin";

    @Override
    public void getConnection() {
            Connection con = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return con;
    }

    @Override
    public void addMeasesurementToDatebase() {

    }
}
