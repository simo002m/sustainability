package com.sustainability;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

public class GraphsController {
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};

    private ArrayList<Integer> oldKms = new ArrayList<Integer>();
    private ArrayList<Integer> newKms = new ArrayList<Integer>();

    private ArrayList<Double> oldTime = new ArrayList<Double>();
    private ArrayList<Double> newTime = new ArrayList<Double>();

    private ArrayList<Integer> savedKms = new ArrayList<Integer>();
    private ArrayList<Double> savedTime = new ArrayList<Double>();

    private int oldRouteKms = 80;
    private double oldRouteMinutes = 135;

    private int minKm = 60;
    private int maxKm = 70;

    private double minTimeMinutes = 70;
    private double maxTimeMinutes = 100;

    @FXML
    private LineChart<String, Integer> kmGraph;
    @FXML
    private LineChart<String, Double> timeGraph;
    @FXML
    private LineChart<String, Integer> kmSavedGraph;
    @FXML
    private LineChart<String, Double> timeSavedGraph;
    @FXML
    private Label kmSavings;
    @FXML
    private Label timeSavings;

    @FXML
    public void initialize() {
        for (int month = 0; month < 12; month++) {
            oldKms.add((new Random().nextInt(90 - oldRouteKms) + oldRouteKms) * 4);
            newKms.add((new Random().nextInt(maxKm - minKm) + minKm) * 4);

            oldTime.add((new Random().nextDouble(160 - oldRouteMinutes) + oldRouteMinutes) * 4);
            newTime.add((new Random().nextDouble(maxTimeMinutes - minTimeMinutes) + minTimeMinutes) * 4);
            System.out.println(newTime.get(month));

            savedKms.add(oldKms.get(month) - newKms.get(month));
            savedTime.add(oldTime.get(month) - newTime.get(month));
        }
        createGraphs();

        int kmSavedYearly = 0;
        double timeSavedYearly = 0;

        for (int i = 0; i < 12; i++) {
            //add monthly kms and time saved
            kmSavedYearly += savedKms.get(i);
            timeSavedYearly += savedTime.get(i) / 60;
        }

        int kmSavedAverageMonthly = kmSavedYearly / 12;
        double timeSavedAverageMonthly = timeSavedYearly / 12;

        String hourOrHours = "timer";
        if (timeSavedAverageMonthly < 2) { hourOrHours = "time"; }

        kmSavings.setText("Årlig besparelse: " + kmSavedYearly + " km\nGennemsnitlig månedlig besparelse: " + kmSavedAverageMonthly + " km");
        timeSavings.setText(String.format("Årlig besparelse: %.1f timer\nGennemsnitlig månedlig besparelse: %.1f %s", timeSavedYearly, timeSavedAverageMonthly, hourOrHours));
    }

    public void createGraphs() {
        XYChart.Series<String, Integer> oldKmsSeries = new XYChart.Series<>();
        XYChart.Series<String, Integer> newKmsSeries = new XYChart.Series<>();
        oldKmsSeries.setName("2024");
        newKmsSeries.setName("2025");

        XYChart.Series<String, Double> oldTimeSeries = new XYChart.Series<>();
        XYChart.Series<String, Double> newTimeSeries = new XYChart.Series<>();
        oldTimeSeries.setName("2024");
        newTimeSeries.setName("2025");

        XYChart.Series<String, Integer> savedKmsSeries = new XYChart.Series<>();
        XYChart.Series<String, Double> savedTimeSeries = new XYChart.Series<>();
        savedKmsSeries.setName("Km");
        savedTimeSeries.setName("Timer");

        for (int i = 0; i < months.length; i++) {
            oldKmsSeries.getData().add(new XYChart.Data<>(months[i], oldKms.get(i)));
            newKmsSeries.getData().add(new XYChart.Data<>(months[i], newKms.get(i)));

            oldTimeSeries.getData().add(new XYChart.Data<>(months[i], oldTime.get(i) / 60));
            newTimeSeries.getData().add(new XYChart.Data<>(months[i], newTime.get(i) / 60));

            savedKmsSeries.getData().add(new XYChart.Data<>(months[i], savedKms.get(i)));
            savedTimeSeries.getData().add(new XYChart.Data<>(months[i], savedTime.get(i) / 60));
        }

        kmGraph.getData().addAll(oldKmsSeries, newKmsSeries);
        timeGraph.getData().addAll(oldTimeSeries, newTimeSeries);
        kmSavedGraph.getData().addAll(savedKmsSeries);
        timeSavedGraph.getData().addAll(savedTimeSeries);
    }

}