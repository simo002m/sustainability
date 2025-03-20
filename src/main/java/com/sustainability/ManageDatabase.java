package com.sustainability;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
            pstmt2.setString(1, measurement.getFillPercentage());
            pstmt2.setString(2, measurement.getId());
            pstmt2.setString(3, measurement.getCompartment());

            int affectedRows = pstmt.executeUpdate();
            affectedRows = affectedRows + pstmt2.executeUpdate();

            if (affectedRows > 0){
                System.out.println("Measurement: " + measurement.getId()+ " was Successfully added to the database.");
            }

            pstmt.close();
            //pstmt2.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to add measurement to the database.");
        }
    }

    @Override
    public List<String> readCollectionLocations(Date collectionDate) throws SQLException {
        List<String> locations = new ArrayList<String>();
        String sqlStatement = "SELECT DISTINCT fldAddress FROM tblBinLocation WHERE fldBinID IN (SELECT tblEmptyings.fldBinID from tblEmptyings INNER JOIN tblCompartments ON tblEmptyings.fldBinID = tblCompartments.fldTrashBinID WHERE tblEmptyings.fldDate = ? AND tblCompartments.fldFillPercentage > 33);";

        PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
        //TBD TBD TBD TBD
        //Temporary:
        locations.add("Campingplads Sønderby");
        locations.add("Campingplads Drejby");
        locations.add("Sommerhusområdet Sønderby");

        return locations;
    }

    @Override
    public ArrayList<FillPercentOverflow> getFillpercentAndOverflow(Date startDate, Date endDate) {
        String sql = "SELECT DISTINCT tblCompartments.fldFillpercent, tblEmptyings.fldOverflow FROM tblCompartments JOIN tblBin ON tblCompartments.fldTrashBinID = tblBin.fldBinID JOIN tblEmptyings ON tblBin.fldBinID = tblEmptyings.fldBinID WHERE tblEmptyings.fldDate BETWEEN ? AND ?";

        ArrayList<FillPercentOverflow> fillPercentOverflowArrayList = new ArrayList<>(); // Initialize the ArrayList

        try {
            getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int fillpercent = rs.getInt("fldFillpercent");
                boolean overflow = rs.getInt("fldOverflow") == 1;

                FillPercentOverflow newObejct = new FillPercentOverflow(fillpercent, overflow);

                fillPercentOverflowArrayList.add(newObejct);
            }
        } catch (Exception e) {}
        int counter = 0;
        for (FillPercentOverflow fillPercentOverflow : fillPercentOverflowArrayList) {

            counter++;
            System.out.println(fillPercentOverflow.getFillPercent() + " " + fillPercentOverflow.isOverflow());
        }
        System.out.println("Number of records bewteen " + startDate + " and " + endDate + " : " + counter);

        return fillPercentOverflowArrayList;
    }


}
