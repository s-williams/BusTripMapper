package test;

import main.Utility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    public void calculateChargeHasCommutativity() {
        assertTrue(Utility.calculateCharge("Stop1", "Stop2") == Utility.calculateCharge("Stop2", "Stop1"));
        assertTrue(Utility.calculateCharge("Stop1", "Stop3") == Utility.calculateCharge("Stop3", "Stop1"));
        assertTrue(Utility.calculateCharge("Stop3", "Stop2") == Utility.calculateCharge("Stop2", "Stop3"));
    }

    @Test
    public void calculateChargeReturnsExpectedChargeForAllTrips() {
        assertEquals(325, Utility.calculateCharge("Stop1", "Stop2"));
        assertEquals(550, Utility.calculateCharge("Stop2", "Stop3"));
        assertEquals(730, Utility.calculateCharge("Stop1", "Stop3"));
    }

    @Test
    public void calculateChargeReturnsExpectedChargeForIncompleteTrips() {
        assertEquals(730, Utility.calculateCharge("Stop1", null));
        assertEquals(550, Utility.calculateCharge("Stop2", null));
        assertEquals(730, Utility.calculateCharge("Stop3", null));
    }

    @Test
    public void calculateChargeThrowsErrorWhenTripInputIsInvalid() {
        assertThrows(RuntimeException.class, () -> Utility.calculateCharge(null, null));
        assertThrows(RuntimeException.class, () -> Utility.calculateCharge(null, "Stop1"));
    }
}