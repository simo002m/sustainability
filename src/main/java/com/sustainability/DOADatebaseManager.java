package com.sustainability;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DOADatebaseManager {

    Connection getConnection();

    void addMeasesurementToDatebase(Measurement measurement) throws SQLException;

    ArrayList<FillPercentOverflow> getFillpercentAndOverflow(java.sql.Date startDate, Date endDate) throws SQLException;
}
