package com.sustainability;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public interface DOADatebaseManager {

    Connection getConnection();

    void addMeasesurementToDatebase(Measurement measurement) throws SQLException;

    List<String> readCollectionLocations(Date collectionDate) throws SQLException;

    ArrayList<FillPercentOverflow> getFillpercentAndOverflow(java.sql.Date startDate, Date endDate) throws SQLException;
}
