package com.sustainability;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.time.Month;
import java.util.ArrayList;
import java.util.Random;

public class GraphsController {
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};

    private ArrayList<Integer> oldKms = new ArrayList<Integer>();
    private ArrayList<Integer> newKms = new ArrayList<Integer>();

    private ArrayList<Integer> oldTime = new ArrayList<Integer>();
    private ArrayList<Integer> newTime = new ArrayList<Integer>();

    private ArrayList<Integer> savedKms = new ArrayList<Integer>();
    private ArrayList<Integer> savedTime = new ArrayList<Integer>();

    private int oldRouteKms = 80;
    private int oldRouteMinutes = 135;

    private int minKm = 60;
    private int maxKm = 70;

    private int minTimeMinutes = 90;
    private int maxTimeMinutes = 100;

    @FXML
    private LineChart<String, Integer> kmGraph;
    @FXML
    private LineChart<String, Integer> timeGraph;
    @FXML
    private LineChart<String, Integer> kmSavedGraph;
    @FXML
    private LineChart<String, Integer> timeSavedGraph;
    @FXML
    private Label kmSavings;
    @FXML
    private Label timeSavings;

    @FXML
    public void initialize() {
        for (int month = 0; month < 12; month++) {
            oldKms.add(oldRouteKms * 4);
            newKms.add((new Random().nextInt(maxKm - minKm) + minKm) * 4);

            oldTime.add(oldRouteMinutes * 4);
            newTime.add((new Random().nextInt(maxTimeMinutes - minTimeMinutes) + minTimeMinutes) * 4);
            System.out.println(newTime.get(month) * 4);

            savedKms.add(oldKms.get(month) - newKms.get(month));
            savedTime.add(oldTime.get(month) - newTime.get(month));
        }
        createGraphs();


        int kmSavedYearly = 0;
        int timeSavedYearly = 0;

        for (int i = 0; i < 12; i++) {
            //add monthly kms and time saved
            kmSavedYearly += savedKms.get(i);
            timeSavedYearly += savedTime.get(i) / 60;
        }

        int kmSavedAverageMonthly = kmSavedYearly / 12;
        int timeSavedAverageMonthly = timeSavedYearly / 12;

        String hourOrHours = "timer";
        if (timeSavedAverageMonthly < 2) { hourOrHours = "time"; }

        kmSavings.setText("Årlig besparelse: " + kmSavedYearly + " km\nGennemsnitlig månedlig besparelse: " + kmSavedAverageMonthly + " km");
        timeSavings.setText("Årlig besparelse: " + timeSavedYearly + " timer\nGennemsnitlig månedlig besparelse: " + timeSavedAverageMonthly + " " + hourOrHours);
    }

    public void createGraphs() {
        XYChart.Series<String, Integer> oldKmsSeries = new XYChart.Series<>();
        XYChart.Series<String, Integer> newKmsSeries = new XYChart.Series<>();
        oldKmsSeries.setName("2024");
        newKmsSeries.setName("2025");

        XYChart.Series<String, Integer> oldTimeSeries = new XYChart.Series<>();
        XYChart.Series<String, Integer> newTimeSeries = new XYChart.Series<>();
        oldTimeSeries.setName("2024");
        newTimeSeries.setName("2025");

        XYChart.Series<String, Integer> savedKmsSeries = new XYChart.Series<>();
        XYChart.Series<String, Integer> savedTimeSeries = new XYChart.Series<>();
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