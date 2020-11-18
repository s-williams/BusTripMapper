package test;

import main.BusTripMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusTripMapperTest {

    @Test
    public void emptyFileIsEmpty() {
        BusTripMapper busTripMapper = new BusTripMapper("resources/empty.csv");
        busTripMapper.readFile();

        //TODO
    }

    @Test
    public void oneTripReturnsOneTrip() {
        BusTripMapper busTripMapper = new BusTripMapper("resources/one_trip.csv");
        busTripMapper.readFile();

        //TODO
    }

}