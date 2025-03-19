package com.sustainability;

public class Measurement {
    private String id;
    private String date;
    private String overFlow;
    private String lastEmptied;
    private String wasAccessible;
    private String compartment;
    private String fillPercentage;


    public Measurement(String id, String date, String overFlow, String lastEmptied, String wasAccessible, String compartment, String fillPercentage) {
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
        return "Measurements{ " +
                "id='" + id +
                ", date= " + date +
                ", overFlow= " + overFlow +
                ", lastEmptied= " + lastEmptied +
                ", wasAccessible= " + wasAccessible +
                ", compartment= " + compartment +
                ", fillPercentage= " + fillPercentage + " } ";}
    }

