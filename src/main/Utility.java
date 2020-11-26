package main;

import main.model.Tap;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utility {

    /**
     * Calculate the charge, in cents, of a bus trip between two stops
     * @param fromStop
     * @param toStop
     * @return
     */
    public static int calculateCharge(String fromStop, String toStop) {
        if (fromStop == null) throw new RuntimeException("fromStop cannot be null");

        // Cancelled trip
        if (fromStop.equals(toStop)) return 0;

        // toStop is null, incomplete trip, charge fullest amount
        if (toStop == null) {
            switch(fromStop) {
                case "Stop1":
                case "Stop3":
                    return 730;
                case "Stop2":
                    return 550;
            }
        }

        // Complete trip
        String pair = fromStop + toStop;
        if (pair.contains("Stop1") && pair.contains("Stop2")) return 325;
        if (pair.contains("Stop2") && pair.contains("Stop3")) return 550;
        if (pair.contains("Stop1") && pair.contains("Stop3")) return 730;

        //unreachable
        System.err.println("Cannot");
        return 0;
    }

    /**
     * Creates a tap object from a String containing a line of csv
     *
     * @param line CSV
     * @return tap object instance
     */
    public static Tap createTap(String line) {
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
