package com.sustainability;

import java.sql.Connection;
import java.sql.SQLException;

public interface DOADatebaseManager {

    Connection getConnection();

    void addMeasesurementToDatebase(Measurement measurement) throws SQLException;

}
