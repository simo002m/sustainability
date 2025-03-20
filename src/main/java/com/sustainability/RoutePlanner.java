package com.sustainability;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoutePlanner {

    private final double DIST_BROAGER_DREJBY = 29.8;

    private final double DIST_DREJBY_SONDERKOBBEL = 3.3;
    private final double DIST_DREJBY_OSTERBY = 6.8;
    private final double DIST_DREJBY_SONDERBYCAMPING = 9;
    private final double DIST_DREJBY_SONDERBYSOMMERHUS = 9;

    private final double DIST_SONDERKOBBEL_OSTERBY = 4.2;
    private final double DIST_SONDERKOBBEL_SONDERBYCAMPING = 6.4;
    private final double DIST_SONDERKOBBEL_SONDERBYSOMMERHUS = 6.6;
    private final double DIST_SONDERKOBBEL_BROAGER = DIST_BROAGER_DREJBY + DIST_DREJBY_SONDERKOBBEL;

    private final double DIST_OSTERBY_SONDERBYCAMPING = 2.4;
    private final double DIST_OSTERBY_SONDERBYSOMMERHUS = 2.7;
    private final double DIST_OSTERBY_BROAGER = DIST_BROAGER_DREJBY + DIST_DREJBY_OSTERBY;

    private final double DIST_SONDERBYCAMPING_SONDERBYSOMMERHUS = 0.5;
    private final double DIST_SONDERBYCAMPING_BROAGER = DIST_BROAGER_DREJBY + DIST_DREJBY_SONDERBYCAMPING;

    private final double DIST_SONDERBYSOMMERHUS_BROAGER = DIST_DREJBY_SONDERBYSOMMERHUS + DIST_DREJBY_SONDERBYSOMMERHUS;

    private final int AVERAGE_KM_HOUR = 55;

    private boolean sonderkobbel;
    private boolean osterby;
    private boolean sonderbycam;
    private boolean sonderbysomhus;

    public Route planRoute(Date collectionDate) throws SQLException {
        Route r;
        double dist;
        List<String> locations = new ArrayList<>();

        DOADatebaseManager dbAccess = new ManageDatabase();
        locations = dbAccess.readCollectionLocations(collectionDate);

        //find shortest route from drejby to locations and back
        if (locations.isEmpty()) {
            r = new Route(0, 0);
            return r;
        }

        else if (locations.size() == 5) {
            r = new Route();
        }

        //times shortest route with AVERAGE_KM_HOUR

        //initialise returnroute

        r = new Route(60,50);
        return r;
    }
}

