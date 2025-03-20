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
    private ArrayList<Measurement> data;

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
            for (Measurement measurement : data) {
                // finds all siteIDs that are not currently added to siteDDL
                if (!siteDDL.getItems().contains(String.valueOf(measurement.getID()))) {
                    // convert int to String, since choicebox only takes Strings
                    siteDDL.getItems().add(String.valueOf(measurement.getID()));
                }
            }

            siteDDL.setValue(String.valueOf(data.get(0).getID()));

            // Ensure both charts start hidden
            monthGraph.setVisible(false);
            yearGraph.setVisible(false);
    }

    // The on action for the search button
    public void createChartClick() {
        ArrayList<Integer> totalsWhs = new ArrayList<>();
        ArrayList<Integer> times = new ArrayList<>();
        HashMap<Integer, Integer> dailyTotals = new HashMap<>();

        diagramTypeDDL.setText("Bar Chart"); // Sets "default" for the text field
        errorMessage.setText(""); // hide error message
        productionTotal.setText(""); // Resets the production total label

        LocalDate datePicked = dateDP.getValue();
        int siteIDPicked = Integer.parseInt(siteDDL.getValue()); // get siteID from sites choice box and convert it to int

        for (SolarData solarData : data) {
            // Check for the selected site and date match
            if (siteIDPicked == solarData.getSiteID()) {
                // Add to day chart data
                if (datePicked.equals(solarData.getDate())) {
                    totalsWhs.add(solarData.getWattPerHour());
                    times.add(solarData.getTime());
                }

                // Add to month chart data
                if (datePicked.getMonth() == solarData.getDate().getMonth() && datePicked.getYear() == solarData.getDate().getYear())
                {
                    int day = solarData.getDate().getDayOfMonth();
                    dailyTotals.put(day, dailyTotals.getOrDefault(day, 0) + solarData.getWattPerHour());
                }
            }
            // Calculations for monthly production
            double totalProductionMonth = 0;
            for (int dayTotal : dailyTotals.values()) {
                totalProductionMonth += dayTotal;
            }

            productionTotal.setText(String.valueOf(totalProductionMonth / 1000) + " kWh"); // convert to String
        }

        // Generate charts if data is available
        if (totalsWhs.isEmpty() && dailyTotals.isEmpty()) {
            errorMessage.setText("No data for chosen date or month.");
        } else {
            createMonthGraph(siteIDPicked, datePicked, totalsWhs, times);
            createYearGraph(siteIDPicked, datePicked, dailyTotals);
        }


    }

    // Create the day chart
    public void createMonthGraph(int siteIDPicked, LocalDate datePicked, ArrayList<Integer> totalWhs, ArrayList<Integer> times) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Site ID: " + siteIDPicked + "\nDate: " + datePicked.toString());

        for (int i = 0; i < totalWhs.size(); i++) {
            series.getData().add(new XYChart.Data<>(times.get(i) + ":00", totalWhs.get(i)));
        }

        monthGraph.setData(FXCollections.observableArrayList(series));
    }

    // Create the month chart
    public void createYearGraph(int siteIDPicked, LocalDate datePicked, HashMap<Integer, Integer> dailyTotals) {
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
