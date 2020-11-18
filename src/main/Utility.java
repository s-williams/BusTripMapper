package main;

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
}
