package com.sustainability;

public class Route {
    private int timeInMinutes = 135;
    private double lengthInKm = 80;

    public Route(int timeInMinutes, double lengthInKm) {
        this.timeInMinutes = timeInMinutes;
        this.lengthInKm = lengthInKm;
    }

    public Route() {}

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public double getLengthInKm() {
        return lengthInKm;
    }
}
