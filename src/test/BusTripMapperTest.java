package test;

import main.BusTripCsvHandler;
import main.BusTripMapper;
import main.model.Tap;
import main.model.Trip;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusTripMapperTest {

    @Test
    public void noTripsCsvReturnsNoTrips() {
        BusTripCsvHandler busTripCsvHandler = new BusTripCsvHandler();
        List<Tap> taps = busTripCsvHandler.readFile("empty.csv");
        assertEquals(0, taps.size());

        BusTripMapper busTripMapper = new BusTripMapper();
        List<Trip> trips = busTripMapper.mapTrips(taps);
        assertEquals(0, trips.size());
    }

    @Test
    public void oneTripCsvReturnsOneTrip() {
        BusTripCsvHandler busTripCsvHandler = new BusTripCsvHandler();
        List<Tap> taps = busTripCsvHandler.readFile("one_trip.csv");
        assertEquals(2, taps.size());

        BusTripMapper busTripMapper = new BusTripMapper();
        List<Trip> trips = busTripMapper.mapTrips(taps);
        assertEquals(1, trips.size());
        assertEquals("COMPLETE", trips.get(0).getStatus());
        assertEquals(325, trips.get(0).getChargeAmount());
    }

    @Test
    public void twoTripsOneCustomerCsvReturnsTwoTrips() {
        BusTripCsvHandler busTripCsvHandler = new BusTripCsvHandler();
        List<Tap> taps = busTripCsvHandler.readFile("two_trips_one_customer.csv");
        assertEquals(4, taps.size());

        BusTripMapper busTripMapper = new BusTripMapper();
        List<Trip> trips = busTripMapper.mapTrips(taps);
        assertEquals(2, trips.size());
        assertEquals("COMPLETE", trips.get(0).getStatus());
        assertEquals("COMPLETE", trips.get(1).getStatus());
        assertTrue(trips.get(0).getPan().equals(trips.get(1).getPan()));
    }

    @Test
    public void twoTripsTwoCustomersReturnsTwoTrips() {
        BusTripCsvHandler busTripCsvHandler = new BusTripCsvHandler();
        List<Tap> taps = busTripCsvHandler.readFile("two_trips_two_customers.csv");
        assertEquals(4, taps.size());

        BusTripMapper busTripMapper = new BusTripMapper();
        List<Trip> trips = busTripMapper.mapTrips(taps);
        assertEquals(2, trips.size());
        assertEquals("COMPLETE", trips.get(0).getStatus());
        assertEquals("COMPLETE", trips.get(1).getStatus());
        assertFalse(trips.get(0).getPan().equals(trips.get(1).getPan()));
    }

    @Test
    public void twoTripsTwoCustomersOneIncompleteCsvReturnsTwoTripsOneIncomplete() {
        BusTripCsvHandler busTripCsvHandler = new BusTripCsvHandler();
        List<Tap> taps = busTripCsvHandler.readFile("two_trips_two_customers_one_incomplete.csv");
        assertEquals(3, taps.size());

        BusTripMapper busTripMapper = new BusTripMapper();
        List<Trip> trips = busTripMapper.mapTrips(taps);
        assertEquals(2, trips.size());
        assertEquals("INCOMPLETE", trips.get(0).getStatus());
        assertEquals("COMPLETE", trips.get(1).getStatus());
        assertFalse(trips.get(0).getPan().equals(trips.get(1).getPan()));
    }

    @Test
    public void oneTripCancelledCsvReturnsOneCancelledTrip() {
        BusTripCsvHandler busTripCsvHandler = new BusTripCsvHandler();
        List<Tap> taps = busTripCsvHandler.readFile("one_trip_cancelled.csv");
        assertEquals(2, taps.size());

        BusTripMapper busTripMapper = new BusTripMapper();
        List<Trip> trips = busTripMapper.mapTrips(taps);
        assertEquals(1, trips.size());
        assertEquals("CANCELLED", trips.get(0).getStatus());
        assertEquals(0, trips.get(0).getChargeAmount());
    }

    @Test
    public void complexCsvReturnsCorrectNumberOfTrips() {
        BusTripCsvHandler busTripCsvHandler = new BusTripCsvHandler();
        List<Tap> taps = busTripCsvHandler.readFile("complex.csv");
        assertEquals(9, taps.size());

        BusTripMapper busTripMapper = new BusTripMapper();
        List<Trip> trips = busTripMapper.mapTrips(taps);
        assertEquals(5, trips.size());
    }
}