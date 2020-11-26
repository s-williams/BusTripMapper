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

    private final List<Tap> taps;
    private final List<Trip> trips;

    public BusTripMapper() {
        this.taps = new ArrayList<>();
        this.trips = new ArrayList<>();
    }

    /**
     * Map the credit card payment taps to a list of bus trips.
     * @return the list of bus trips
     */
    public List<Trip> mapTrips(List<Tap> taps) {
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
                    trip.setStatus("CANCELLED");
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
        return trips;
    }
}
