package com.sustainability;

public class TimeCalculator {

    private int bin;
    private final int timePerBinAndBetweenHouse = 1;
    private int numberOfLocations;
    private final double timeBetweenHouse = 0.25;
    private double totalTimeInMinutes;
    private final int timeForStandardRouteWithoutBinsCollected;

    // Standard route without bins collected = 1h 27min.
    // Standard route with 50 bins collected on all 5 locations = 2h 15min. Should be a variable to use it in the graph.

    /**
     * this is a time caluculator for the route
     * @param numberOfLocations how many places where garbage need to be collected.
     * @param bin number of bins that need to get collected.
     */
    public TimeCalculator(int numberOfLocations, int bin, int timeForStandardRouteWithoutBinsCollected) {
       this.numberOfLocations = numberOfLocations;
       this.bin = bin;
       this.timeForStandardRouteWithoutBinsCollected = timeForStandardRouteWithoutBinsCollected;
    }

    public double getTotalTimeInMinutes() {
        totalTimeInMinutes = (bin * timePerBinAndBetweenHouse - (timeBetweenHouse * numberOfLocations) + timeForStandardRouteWithoutBinsCollected);
        return totalTimeInMinutes;
    }
}
