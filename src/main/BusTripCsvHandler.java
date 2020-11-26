package main;

import main.model.Tap;
import main.model.Trip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BusTripCsvHandler {

    public BusTripCsvHandler() {
    }

    /**
     * Read the CSV file and map the contents of the file to a list credit card payment tap objects.
     * @param inputFileName
     * @return the list of credit card payment taps
     */
    public List<Tap> readFile(String inputFileName) {
        List<Tap> taps = new ArrayList<>();
        try {
            Files.lines(Path.of("resources/" + inputFileName))
                    .skip(1) // Ignore first line
                    .forEach(l -> taps.add(Utility.createTap(l)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taps;
    }

    /**
     * Write the list of trips to a CSV file.
     * @param outputFileName
     * @param trips
     */
    private void writeFile(String outputFileName, List<Trip> trips) {
        File csvOutput = new File("out/" + outputFileName);
        csvOutput.delete();
        try {
            PrintWriter pw = new PrintWriter(csvOutput);
            pw.println("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status");
            trips.forEach(trip -> pw.println(trip.toCsv()));
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
