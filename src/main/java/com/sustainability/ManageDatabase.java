package com.sustainability;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


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
    public void addMeasesurementToDatebase(Measurement measurement)  {
        String sql = "INSERT INTO dbo.tblEmptyings (fldDate, fldOverflow, fldWasAccessible, fldBinID) VALUES (?, ?, ? , ?)";
        String sql2 = "INSERT INTO dbo.tblCompartments (fldFillpercent, fldTrashBinID, fldCompartmentNO) VALUES (?, ?, ?)";
        getConnection();
        try {
            //Added data in tblEmptyings
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, measurement.getDate());
            pstmt.setString(2, measurement.getOverFlow());
            pstmt.setString(3, measurement.getWasAccessible());
            pstmt.setString(4, measurement.getId());

            //added data in tblCompartments
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt.setString(1, measurement.getFillPercentage());
            pstmt.setString(2, measurement.getId());
            pstmt.setString(3, measurement.getCompartment());

            int affectedRows = pstmt.executeUpdate();
            affectedRows = affectedRows + pstmt2.executeUpdate();

            if (affectedRows > 7){
                System.out.println("Measurement: " + measurement.getId()+ " was Successfully added to the database.");
            }

            pstmt.close();
            pstmt2.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to add measurement to the database.");
        }
    }

}
