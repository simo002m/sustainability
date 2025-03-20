package com.sustainability;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

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

    private int minTimeMinutes = 100;
    private int maxTimeMinutes = 110;

    @FXML
    private LineChart<String, Integer> kmGraph;
    @FXML
    private LineChart<String, Integer> timeGraph;
    @FXML
    private LineChart<String, Integer> savingsGraph;

    @FXML
    public void initialize() {
        for (int month = 0; month < 12; month++) {
            oldKms.add(oldRouteKms * 4);
            newKms.add((new Random().nextInt(maxKm - minKm) + minKm) * 4);

            oldTime.add(oldRouteMinutes * 4);
            newTime.add((new Random().nextInt(maxTimeMinutes - minTimeMinutes) + minTimeMinutes) * 4);

            savedKms.add(oldKms.get(month) - newKms.get(month));
            savedTime.add(oldTime.get(month) - newTime.get(month));
        }
        createGraphs();
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
        savingsGraph.getData().addAll(savedKmsSeries, savedTimeSeries);
    }


}