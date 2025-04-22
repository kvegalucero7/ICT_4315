/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 20, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import ict4305.university.parking.charges.strategy.HourlyRateStrategy;

class ParkingTransactionTest {

	@Test
	public void testValidTransaction() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0,
				600, new HourlyRateStrategy());
		ParkingTransaction transaction = new ParkingTransaction(Instant.now(), permit, lot);
		
		assertNotNull(transaction); // validation creation of transaction
		assertEquals(permit, transaction.getPermit()); // validating permit in transaction
		assertEquals(lot, transaction.getLot()); // validating lot in transaction
		assertNotNull(transaction.getCharge()); // validating charge in transaction
	}
	
	
	// testing null parameters: when, permit, lot
	@Test
	public void testNullParametersThrowsException() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(null, permit,lot));
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(Instant.now(), null, lot));
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(Instant.now(), permit, null));
	}
	
	// Testing charge for a compact car 
	@Test
	public void testChargeCalculationForCompactCar() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0,
				600, new HourlyRateStrategy());
		ParkingTransaction transaction = new ParkingTransaction(Instant.now(), permit, lot);
		
		assertNotNull(transaction.getCharge()); // validating creation
		assertEquals(true, transaction.getCharge().getCents() > 0);
	}
	
	// Testing charge for a SUV car 
	@Test
	public void testChargeCalculationForSUVCar() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0,
				600, new HourlyRateStrategy());
		ParkingTransaction transaction = new ParkingTransaction(Instant.now(), permit, lot);
		
		assertNotNull(transaction.getCharge()); // validating creation
		assertEquals(true, transaction.getCharge().getCents() > 0);
	}
	
	
	// TEsting equals and hashcode comparisons
	@Test
    public void testEqualsAndHashCode() {
        Instant now = Instant.now();
        Car car = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
        ParkingPermit permit = new ParkingPermit(car);
        ParkingLot lot = new ParkingLot("Lot1", new Address("456 Elm St", null, "Cityville", "CA", "90210"), 150, 0,
        		600, new HourlyRateStrategy());

        ParkingTransaction transaction1 = new ParkingTransaction(now, permit, lot);
        ParkingTransaction transaction2 = new ParkingTransaction(now, permit, lot);
        ParkingTransaction transaction3 = new ParkingTransaction(Instant.now().plusSeconds(20), permit, lot);

        // Test equality
        assertEquals(transaction1, transaction2);
        assertNotEquals(transaction1, transaction3);

        // Test hashCode
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
        assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

	//testing equals with null and a string
    @Test
    public void testNotEqualsForNullAndString() {
        Instant now = Instant.now();
        Car car = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
        ParkingPermit permit = new ParkingPermit(car);
        ParkingLot lot = new ParkingLot("Lot1", new Address("456 Elm St", null, "Cityville", "CA", "90210"), 150, 0,
        		600, new HourlyRateStrategy());

        ParkingTransaction transaction = new ParkingTransaction(now, permit, lot);

        assertNotEquals(transaction, null);
        assertNotEquals(transaction, "Test String");
    }
}
