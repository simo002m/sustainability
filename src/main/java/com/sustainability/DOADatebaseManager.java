package com.sustainability;

import java.sql.Connection;

public interface DOADatebaseManager {

    Connection getConnection();

    void addMeasesurementToDatebase();

}
