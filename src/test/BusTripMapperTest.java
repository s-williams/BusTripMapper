package test;

import main.BusTripMapper;
import main.model.Tap;
import main.model.Trip;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BusTripMapperTest {

    @Test
    public void noTrips() {
        BusTripMapper busTripMapper = new BusTripMapper("empty.csv");
        List<Tap> taps = busTripMapper.readFile();
        assertEquals(0, taps.size());

        List<Trip> trips = busTripMapper.mapTrips();
        assertEquals(0, trips.size());
    }

    @Test
    public void oneTrip() {
        BusTripMapper busTripMapper = new BusTripMapper("one_trip.csv");
        List<Tap> taps = busTripMapper.readFile();
        assertEquals(2, taps.size());

        List<Trip> trips = busTripMapper.mapTrips();
        assertEquals(1, trips.size());
        assertEquals("COMPLETE", trips.get(0).getStatus());
        assertEquals(325, trips.get(0).getChargeAmount());
    }

    @Test
    public void twoTripsOneCustomer() {
        BusTripMapper busTripMapper = new BusTripMapper("two_trips_one_customer.csv");
        List<Tap> taps = busTripMapper.readFile();
        assertEquals(4, taps.size());

        List<Trip> trips = busTripMapper.mapTrips();
        assertEquals(2, trips.size());
        assertEquals("COMPLETE", trips.get(0).getStatus());
        assertEquals("COMPLETE", trips.get(1).getStatus());
        assertTrue(trips.get(0).getPan().equals(trips.get(1).getPan()));
    }

    @Test
    public void twoTripsTwoCustomers() {
        BusTripMapper busTripMapper = new BusTripMapper("two_trips_two_customers.csv");
        List<Tap> taps = busTripMapper.readFile();
        assertEquals(4, taps.size());

        List<Trip> trips = busTripMapper.mapTrips();
        assertEquals(2, trips.size());
        assertEquals("COMPLETE", trips.get(0).getStatus());
        assertEquals("COMPLETE", trips.get(1).getStatus());
        assertFalse(trips.get(0).getPan().equals(trips.get(1).getPan()));
    }

    @Test
    public void twoTripsTwoCustomersOneIncomplete() {
        BusTripMapper busTripMapper = new BusTripMapper("two_trips_two_customers_one_incomplete.csv");
        List<Tap> taps = busTripMapper.readFile();
        assertEquals(3, taps.size());

        List<Trip> trips = busTripMapper.mapTrips();
        assertEquals(2, trips.size());
        assertEquals("INCOMPLETE", trips.get(0).getStatus());
        assertEquals("COMPLETE", trips.get(1).getStatus());
        assertFalse(trips.get(0).getPan().equals(trips.get(1).getPan()));
    }

}