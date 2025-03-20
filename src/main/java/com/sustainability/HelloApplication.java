package com.sustainability;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.sustainability.ReadFile.readCVSFile;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 988, 550);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        DOADatebaseManager datebaseManager = new ManageDatabase();
        datebaseManager.getConnection();

        ArrayList<Measurement> measurements = new ArrayList<>(readCVSFile("C:\\Users\\jakob\\Desktop\\FHA\\sustainability\\src\\main\\resources\\com\\sustainability\\CVSFile\\trash_bin_measurements_v2.txt"));
        for (Measurement measurement : measurements){
            datebaseManager.addMeasesurementToDatebase(measurement);
        }

        launch();
    }
}