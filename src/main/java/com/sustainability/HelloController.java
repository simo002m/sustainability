package com.sustainability;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private DatePicker ChooseDate1;

    @FXML
    private DatePicker ChooseDate2;

    @FXML
    private BarChart <String, Number> BarChart1;

    @FXML
    private BarChart <Number, String> BarChart2;

    @FXML
    private Button GetData;

    @FXML
    private Button showGraph;

    @FXML
    public void initialize() {
        // Load the CSS file
        BarChart1.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/sustainability/BarChart.css")).toExternalForm());
    }

    @FXML
    public void GetDataOnClick(ActionEvent event) {
        final String API_URL = "http://10.176.69.182:3000/api/bins/download/"; // API base URL (til Marcs IP (kan ændre sig))
        final String SAVE_FOLDER = System.getProperty("user.dir") + "/csv_files/"; // Folder in project root

        String fileUrl = API_URL + "";
            String savePath = SAVE_FOLDER + "2025-03-20" + ".csv";

            try {
                // Sikrer at jeres path eksisterer.
                Files.createDirectories(Paths.get(SAVE_FOLDER));

                // Laver en URL connection til at "API" call
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Tjekker om vi får en OK (kode 200)
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (InputStream inputStream = connection.getInputStream();
                         FileOutputStream outputStream = new FileOutputStream(savePath)) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        System.out.println("CSV downloaded successfully: " + savePath);
                    }
                } else {
                    System.out.println("Failed to download CSV. Server responded with: " + responseCode);
                }

                connection.disconnect();
            } catch (IOException e) {
                System.out.println("Error downloading CSV: " + e.getMessage());
            }
    }
    @FXML
    public void ShowBarChart(ArrayList<FillPercentOverflow> percentOverflow) {
        // Blink green
        int overflowCounter = 0;
        // Red
        int over90Counter = 0;
        // Blink yellow
        int over33Counter = 0;
        // Green
        int underEqual90Counter = 0;

        for (FillPercentOverflow checklist : percentOverflow) {
            if (checklist.isOverflow()) {
                overflowCounter++;
            }
            if (checklist.getFillPercent() > 90) {
                over90Counter++;
            }
            if (checklist.getFillPercent() <= 90) {
                underEqual90Counter++;
            }
            if (checklist.getFillPercent() >= 33) {
                over33Counter++;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        String GreenName = "Green: " + underEqual90Counter;
        String GreenBlinking = "Overflow(Green Blink): " + overflowCounter;
        String RedName = "Red: " + over90Counter;
        String PickUp = "PickUp: " + over33Counter;

        XYChart.Data<String, Number> greenBar = new XYChart.Data<>(GreenName , underEqual90Counter);
        XYChart.Data<String, Number> overflowBar = new XYChart.Data<>(GreenBlinking, overflowCounter);
        XYChart.Data<String, Number> redBar = new XYChart.Data<>(RedName, over90Counter);
        XYChart.Data<String, Number> yellowBar = new XYChart.Data<>(PickUp, over33Counter);

        series.getData().addAll(greenBar, overflowBar, redBar, yellowBar);

        // Add series to BarChart
        BarChart1.getData().addAll(series);

        // Apply colors after nodes are created
        Platform.runLater(() -> {
            greenBar.getNode().setStyle("-fx-bar-fill: lightgreen;");
            overflowBar.getNode().setStyle("-fx-bar-fill: darkgreen;");
            redBar.getNode().setStyle("-fx-bar-fill: red;");
            yellowBar.getNode().setStyle("-fx-bar-fill: yellow;");
        });
    }

    @FXML
    public void ShowGraphs(ActionEvent event) throws SQLException {
        ArrayList<FillPercentOverflow> percentOverflow = new ArrayList<>();

        java.sql.Date startDate = Date.valueOf("2025-02-17");
        java.sql.Date endDate = Date.valueOf("2025-02-25");

        DOADatebaseManager dbman = new ManageDatabase();
        percentOverflow = dbman.getFillpercentAndOverflow(startDate,endDate);

        ShowBarChart(percentOverflow);
    }

    public void openGraphsStage() throws IOException
    {
        Stage graphsStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("graphs.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load(), 900, 700);
        graphsStage.setTitle("Grafer");
        graphsStage.setResizable(false);
        graphsStage.setScene(scene2);
        graphsStage.show();
    }
}