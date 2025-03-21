package com.sustainability;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile{

    /**
     * Reads a file located at a given path to create SolarData objects
     * witch are then returned as a list
     * @param filepath Path to the file to read
     * @return ArrayList of SolarData to be used in program
     * @throws FileNotFoundException if the given path does not exists
     */
    public static ArrayList<Measurement> readCVSFile(String filepath) throws FileNotFoundException
    {
        String id;
        String date;
        String overFlow;
        String lastEmptied;
        String wasAccessible;
        String compartment;
        String fillPercentage;

        ArrayList<Measurement> measurements = new ArrayList<Measurement>();

        File inputFile = new File(filepath);
        Scanner scanner = new Scanner(inputFile).useDelimiter("[,\\n\\r]");

        scanner.nextLine(); // skip first line (tsv table header)

        while (scanner.hasNextLine())
        {
            // read data from tsv file
            id = scanner.next();
            date = scanner.next();
            overFlow = scanner.next();
            lastEmptied = scanner.next();
            wasAccessible = scanner.next();
            compartment = scanner.next();
            fillPercentage = scanner.next();

            Date dateOnly = convertDatetimeToDate(date);
            Date lastEmptiedDate = convertDatetimeToDate(lastEmptied);

            scanner.nextLine();

            // add measurements to the measurements array
            measurements.add(new Measurement(id, dateOnly, overFlow, lastEmptiedDate, wasAccessible, compartment, fillPercentage));

        }
        for (Measurement measurement : measurements) {
            System.out.println(measurement);
        }
        return measurements;
    }

    public static java.sql.Date convertDatetimeToDate(String dateTime) {
        java.sql.Date dateOnly = Date.valueOf(dateTime.split(" ")[0]);
        System.out.println("CONVERTED DATE: " + dateOnly);
        return dateOnly;
        }
}

