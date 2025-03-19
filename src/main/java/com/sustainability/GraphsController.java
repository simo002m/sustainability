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

public class SolarController {
    private ArrayList<> data;

    //region - Variables for scene builder controls
    @FXML
    private Label errorMessage; // Label to show error message when choosing invalid choice
    @FXML
    private ChoiceBox<String> siteDDL; // DDL: Drop Down List
    @FXML
    private DatePicker dateDP; // DP: Date Picker
    @FXML
    private TextField diagramTypeDDL; // Shows which chart type is showing
    @FXML
    private BarChart<String, Integer> productionBarChart; // Day chart
    @FXML
    private LineChart<String, Integer> productionLineChart; // Month chart
    @FXML
    private Label productionTotal; // Label to calculate kWh
    //endregion


    // Initializes and reads the data file
    @FXML
    public void initialize() {
        try {
            data = ReadData.readFileData("src/resources/solar-dataset.tsv");

            for (SolarData solarData : data) {
                // finds all siteIDs that are not currently added to siteDDL
                if (!siteDDL.getItems().contains(String.valueOf(solarData.getSiteID()))) {
                    // convert int to String, since choicebox only takes Strings
                    siteDDL.getItems().add(String.valueOf(solarData.getSiteID()));
                }
            }

            siteDDL.setValue(String.valueOf(data.get(0).getSiteID()));

            // Ensure both charts start hidden
            productionBarChart.setVisible(false);
            productionLineChart.setVisible(false);

        } catch (FileNotFoundException e) {
            errorMessage.setText("File not found");
        } catch (Exception e) {
            errorMessage.setText(e.getMessage());
        }
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
            createDayChart(siteIDPicked, datePicked, totalsWhs, times);
            createMonthChart(siteIDPicked, datePicked, dailyTotals);
        }

        // Show only the selected chart type
        switch (diagramTypeDDL.getText()) {
            case "Bar Chart": // Shows day graph
                productionBarChart.setVisible(true);
                productionLineChart.setVisible(false);
                break;
            case "Line Chart": // Shows month graph
                productionBarChart.setVisible(false);
                productionLineChart.setVisible(true);
                break;
            default:
        }
    }

    // Create the day chart
    public void createDayChart(int siteIDPicked, LocalDate datePicked, ArrayList<Integer> totalWhs, ArrayList<Integer> times) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Site ID: " + siteIDPicked + "\nDate: " + datePicked.toString());

        for (int i = 0; i < totalWhs.size(); i++) {
            series.getData().add(new XYChart.Data<>(times.get(i) + ":00", totalWhs.get(i)));
        }

        productionBarChart.setData(FXCollections.observableArrayList(series));
    }

    // Create the month chart
    public void createMonthChart(int siteIDPicked, LocalDate datePicked, HashMap<Integer, Integer> dailyTotals) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Site ID: " + siteIDPicked + "\nMonth: " + datePicked.getMonth() + " " + datePicked.getYear());

        for (int day : dailyTotals.keySet()) {
            series.getData().add(new XYChart.Data<>("Day " + day, dailyTotals.get(day)));
        }

        productionLineChart.setData(FXCollections.observableArrayList(series));
    }

    // Button to show the day chart
    public void showDayChart()
    {
        diagramTypeDDL.setText("Bar Chart");
        productionBarChart.setVisible(true);
        productionLineChart.setVisible(false);
    }

    // Button to show the month chart
    public void showMonthChart()
    {
        diagramTypeDDL.setText("Line Chart");
        productionBarChart.setVisible(false);
        productionLineChart.setVisible(true);
    }

}
