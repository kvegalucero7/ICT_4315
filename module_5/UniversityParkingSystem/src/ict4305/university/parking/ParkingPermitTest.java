/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ParkingPermitTest {

	//Testing testing that the car permit was successfully created
	@Test
	public void testValidPermitCreation() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		assertNotNull(permit.getPermitId()); // validating successful permitId
		assertEquals(car, permit.getCar()); // validating car matches car linked to permitId
	}
	
	//Testing a null parking permit
	@Test
	public void testNullCarThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> new ParkingPermit(null));
	}

	// Testing equals and HashCode comparisons
	@Test
    public void testEqualsAndHashCode() {
        Car car1 = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
        Car car2 = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
        Car car3 = new Car("Permit-002", LocalDate.now().plusYears(1), "XYZ789", CarType.SUV, "CI0002");

        ParkingPermit permit1 = new ParkingPermit(car1);
        ParkingPermit permit2 = new ParkingPermit(car2);
        ParkingPermit permit3 = new ParkingPermit(car3);

        // Test equality
        assertEquals(permit1, permit2);
        assertNotEquals(permit1, permit3);

        // Test hashCode
        assertEquals(permit1.hashCode(), permit2.hashCode());
        assertNotEquals(permit1.hashCode(), permit3.hashCode());
    }

	// Testing equals with null and string
    @Test
    public void testNotEqualsForNullAndString() {
        Car car = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
        ParkingPermit permit = new ParkingPermit(car);

        assertNotEquals(permit, null);
        assertNotEquals(permit, "A Car String");
    }

}
