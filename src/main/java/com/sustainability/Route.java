package com.sustainability;

public class Route {
    private int timeInMinutes;
    private double lengthInKm;

    public Route(int timeInMinutes, double lengthInKm) {
        this.timeInMinutes = timeInMinutes;
        this.lengthInKm = lengthInKm;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public double getLengthInKm() {
        return lengthInKm;
    }
}
