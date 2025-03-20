package com.sustainability;

import java.sql.Date;

public class Measurement {
    private String id;
    private Date date;
    private String overFlow;
    private Date lastEmptied;
    private String wasAccessible;
    private String compartment;
    private String fillPercentage;

    public String getId() {
        return id;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public String getOverFlow() {
        return overFlow;
    }

    public Date getLastEmptied() {
        return lastEmptied;
    }

    public String getWasAccessible() {
        return wasAccessible;
    }

    public String getCompartment() {
        return compartment;
    }

    public String getFillPercentage() {
        return fillPercentage;
    }

    public Measurement(String id, Date date, String overFlow, Date lastEmptied, String wasAccessible, String compartment, String fillPercentage) {
        this.id = id;
        this.date = date;
        this.overFlow = overFlow;
        this.lastEmptied = lastEmptied;
        this.wasAccessible = wasAccessible;
        this.compartment = compartment;
        this.fillPercentage = fillPercentage;
    }

    @Override
    public String toString() {
        return "Measurements { " +
                "id='" + id +
                ", date= " + date +
                ", overFlow= " + overFlow +
                ", lastEmptied= " + lastEmptied +
                ", wasAccessible= " + wasAccessible +
                ", compartment= " + compartment +
                ", fillPercentage= " + fillPercentage + " } ";
    }
}

