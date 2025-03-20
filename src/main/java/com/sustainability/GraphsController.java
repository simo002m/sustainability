package com.sustainability;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphsController {
    private ArrayList<TimeCalculater> data;

    @FXML
    private Label errorMessage; // Label to show error message when choosing invalid choice
    @FXML
    private ChoiceBox<String> siteDDL; // DDL: Drop Down List
    @FXML
    private DatePicker dateDP; // DP: Date Picker
    @FXML
    private LineChart<String, Integer> monthGraph; // Day chart
    @FXML
    private LineChart<String, Integer> yearGraph; // Month chart

    // Initializes and reads the data file
    @FXML
    public void initialize() {
        // Ensure both charts start hidden
        monthGraph.setVisible(false);
        yearGraph.setVisible(false);
    }

    // The on action for the search button
    public void createChartClick() {
        ArrayList<Integer> originalKms = new ArrayList<>();
        ArrayList<Integer> newKms = new ArrayList<>();
        ArrayList<Integer> originalTime = new ArrayList<>();
        ArrayList<Integer> newTime = new ArrayList<>();

        createMonthGraph();
        createYearGraph();
    }

    // Create the day chart
    public void createMonthGraph() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Site ID: " + siteIDPicked + "\nDate: " + datePicked.toString());

        for (int i = 0; i < totalWhs.size(); i++) {
            series.getData().add(new XYChart.Data<>(times.get(i) + ":00", totalWhs.get(i)));
        }

        monthGraph.setData(FXCollections.observableArrayList(series));
    }

    // Create the month chart
    public void createYearGraph() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Site ID: " + siteIDPicked + "\nMonth: " + datePicked.getMonth() + " " + datePicked.getYear());

        for (int day : dailyTotals.keySet()) {
            series.getData().add(new XYChart.Data<>("Day " + day, dailyTotals.get(day)));
        }

        yearGraph.setData(FXCollections.observableArrayList(series));
    }

    // Button to show the day chart
    public void showMonthGraph()
    {
        monthGraph.setVisible(true);
        yearGraph.setVisible(false);
    }

    // Button to show the month chart
    public void showYearGraph()
    {
        monthGraph.setVisible(false);
        yearGraph.setVisible(true);
    }

}
