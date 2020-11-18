package main;

import main.model.Tap;
import main.model.Trip;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusTripMapper {

    private String inputFileName;

    private List<Tap> taps;
    private List<Trip> trips;

    public BusTripMapper(String inputFileName) {
        this.inputFileName = inputFileName;
        this.taps = new ArrayList<>();
        this.trips = new ArrayList<>();
    }

    public List<Tap> readFile() {
        // Read the csv and map to tap objects
        try {
            Files.lines(Path.of(inputFileName))
                    .skip(1) // Ignore first line
                    .forEach(l -> {
                        taps.add(createTap(l));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taps;
    }

    public void mapTrips() {
        for (int i = 0; i < taps.size(); i++) {
            Tap firstStop = taps.get(i);
            Tap secondStop = null;
            if (firstStop.getTapType().equals("ON")) {
                // Get the next off tap with same pan
                List<Tap> samePan = taps.stream()
                        .filter(t -> t.getPan().equals(firstStop.getPan()))
                        .filter(t -> t.getDateTime().after(firstStop.getDateTime()))
                        .filter(t -> t.getTapType().equals("OFF"))
                        .collect(Collectors.toList());
                secondStop = samePan.get(0);

                Trip trip = new Trip();

                // calculate charge and status
                if (secondStop == null) {

                }
            }
        }
        taps.forEach(t -> {
            // For each tap, find a pair tap, then make a trip


            // If the pair is a tap on/tap off at the same stop, no charge

            // If a tap has no pair, and is a tap on, calculate the biggest fare and make that a trip
        });
    }

    /**
     * Creates a tap from a line of csv
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
