package main;

import main.model.Tap;
import main.model.Trip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusTripMapper {

    private final String inputFileName;

    private final List<Tap> taps;
    private final List<Trip> trips;

    public BusTripMapper(String inputFileName) {
        this.inputFileName = inputFileName;
        this.taps = new ArrayList<>();
        this.trips = new ArrayList<>();
    }

    /**
     * @return
     */
    public List<Tap> readFile() {
        // Read the csv and map to tap objects
        try {
            Files.lines(Path.of("resources/" + inputFileName))
                    .skip(1) // Ignore first line
                    .forEach(l -> taps.add(createTap(l)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taps;
    }

    /**
     * @return
     */
    public List<Trip> mapTrips() {
        taps.stream().filter(t -> t.getTapType().equals("ON")).forEach(firstStop -> {
            Tap secondStop;

            // Get the next off tap with same pan
            List<Tap> samePan = taps.stream()
                    .filter(t -> t.getPan().equals(firstStop.getPan()))
                    .filter(t -> t.getCompanyId().equals(firstStop.getCompanyId()))
                    .filter(t -> t.getBusId().equals(firstStop.getBusId()))
                    .filter(t -> t.getDateTime().after(firstStop.getDateTime()))
                    .filter(t -> t.getTapType().equals("OFF"))
                    .collect(Collectors.toList());
            if (samePan.size() > 0) {
                secondStop = samePan.get(0);
            } else {
                secondStop = null;
            }

            Trip trip = new Trip();
            trip.setStarted(firstStop.getDateTime());
            if (secondStop != null) {
                trip.setFinished(secondStop.getDateTime());
                trip.setToStopId(secondStop.getStopId());
                if (firstStop.getStopId().equals(secondStop.getStopId())) {
                    // Stops are the same, therefore cancelled trip
                    trip.setStatus("INCOMPLETE");
                } else {
                    trip.setStatus("COMPLETE");
                }

            } else {
                trip.setFinished(null);
                trip.setToStopId(null);
                trip.setStatus("INCOMPLETE");
            }
            trip.setFromStopId(firstStop.getStopId());
            trip.setCompanyId(firstStop.getCompanyId());
            trip.setBusId(firstStop.getBusId());
            trip.setPan(firstStop.getPan());
            trip.setChargeAmount(Utility.calculateCharge(trip.getFromStopId(), trip.getToStopId()));

            trips.add(trip);
        });
        writeFile();
        return trips;
    }

    private void writeFile() {
        File csvOutput = new File("out/" + inputFileName);
        csvOutput.delete();
        try {
            PrintWriter pw = new PrintWriter(csvOutput);
            pw.println("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status");
            trips.stream().forEach(trip -> pw.println(trip.toCsv()));
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates a tap from a line of csv
     *
     * @param line
     * @return
     */
    public Tap createTap(String line) {
        String[] csv = line.split(",");
        Tap tap = new Tap();
        tap.setId(csv[0].trim());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            tap.setDateTime(sdf.parse(csv[1].trim()));
        } catch (ParseException e) {
            System.err.println("Unable to parse date " + csv[1].trim());
        }
        tap.setTapType(csv[2].trim());
        tap.setStopId(csv[3].trim());
        tap.setCompanyId(csv[4].trim());
        tap.setBusId(csv[5].trim());
        tap.setPan(csv[6].trim());
        return tap;
    }
}
