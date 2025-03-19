package com.sustainability;

public class TimeCalculator {

    private int bin;
    private final int timePerBinAndBetweenHouse = 1;
    private int numberOfLocations;
    private final double timeBetweenHouse = 0.25;
    private double totalTimeInMinutes;
    private final int timeForStandardRoute = 87; // calcuated to be 87minutes

    //standard route plus bins = 2h 15min should be a variable to use it in the graph.

    /**
     * this is a time caluculator for the route
     * @param numberOfLocations how many places where garbage need to be collected.
     * @param bin number of bins that need to get collected.
     */
    public TimeCalculator(int numberOfLocations, int bin) {
       this.numberOfLocations = numberOfLocations;
       this.bin = bin;
    }

    public double getTotalTimeInMinutes() {
        totalTimeInMinutes= (bin * timePerBinAndBetweenHouse - (timeBetweenHouse * numberOfLocations) + timeForStandardRoute);
        return totalTimeInMinutes;
    }
}
