package com.sustainability;

import javafx.fxml.FXML;
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

import javafx.event.ActionEvent;

public class HelloController {
    @FXML
    private DatePicker ChooseDate1;

    @FXML
    private DatePicker ChooseDate2;

    @FXML
    private BarChart <Number, String> BarChart1;

    @FXML
    private BarChart <Number, String> BarChart2;

    @FXML
    private Button GetData;

    @FXML
    private Button showGraph;

    @FXML
    public void initialize() {}

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
    public void ShowBarChart(ArrayList<FillPercentOverflow> percentOverflow){
        //blink green
        int overflowCounter = 0;
        //red
        int over90Counter = 0;
        //blink yellow
        int over33Counter = 0;
        //green
        int underEqual90Counter = 0;

        for (FillPercentOverflow checklist : percentOverflow){
            if (checklist.isOverflow()){
                overflowCounter++;
            }

            if(checklist.getFillPercent() > 90 ){
                over90Counter++;
            }
            if(checklist.getFillPercent() <= 90){
                underEqual90Counter++;
            }
            if(checklist.getFillPercent() >= 33){
                over33Counter++;
            }
        }

        XYChart.Series series = new XYChart.Series();
        //series.setName("Dato: " + DatePicker.getValue().toString() + " & Site ID: " + getSiteID());

        //adds data to the series hourly

        series.getData().add(new XYChart.Data<>("Green",underEqual90Counter));
        series.getData().add(new XYChart.Data<>("Overflow(Green Blink)",overflowCounter));
        series.getData().add(new XYChart.Data<>("Red",over90Counter));
        series.getData().add(new XYChart.Data<>("Pickup(Yellow Blink)",over33Counter));


        BarChart1.getData().addAll(series);
    }

    @FXML
    public void ShowGraph(ActionEvent event) throws SQLException {
        ArrayList<FillPercentOverflow> percentOverflow = new ArrayList<>();

        java.sql.Date startDate = Date.valueOf("2025-02-17");
        java.sql.Date endDate = Date.valueOf("2025-02-25");

        DOADatebaseManager dbman = new ManageDatabase();
        percentOverflow = dbman.getFillpercentAndOverflow(startDate,endDate);

        ShowBarChart(percentOverflow);
    }
}