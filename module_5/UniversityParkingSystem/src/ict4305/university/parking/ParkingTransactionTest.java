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

//import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import ict4305.university.parking.charges.strategy.HourlyRateStrategy;

class ParkingTransactionTest {

	@Test
	public void testValidParkingTransactionCreation() {
		LocalDateTime entryTime = LocalDateTime.of(2025, 5, 1, 10, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 5, 1, 15, 0);
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		Money charge = new Money(5000); //$50.00 charge
		
		//ParkingPermit permit = new ParkingPermit(car);
		//ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0,
			//	600, new HourlyRateStrategy());
		ParkingTransaction transaction = new ParkingTransaction(entryTime, exitTime, car, charge);
		
		assertNotNull(transaction); // validation creation of transaction
		assertEquals(entryTime, transaction.getEntryTime()); // validating an entry time
		assertEquals(exitTime, transaction.getExitTime()); // validating an exit time
		assertNotNull(transaction.getCharge()); // validating charge in transaction
		assertEquals(charge, transaction.getCharge()); // validating charge values match
	}
	
	
	// testing null parameters: when, permit, lot
	@Test
	public void testNullParametersThrowsException() {
		LocalDateTime entryTime = LocalDateTime.of(2025, 5, 1, 10, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 5, 1, 15, 0);
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		Money charge = new Money(5000); //$50.00 charge
		
		/*Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());*/
		
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(null, exitTime, car, charge));
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(entryTime, null, car, charge));
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(entryTime, exitTime, null, charge));
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(entryTime, exitTime, car, null));
	}
	
	//testing invalid exit time
	@Test
	public void testInvalidExitTime() {
		LocalDateTime entryTime = LocalDateTime.of(2025, 5, 1, 15, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 5, 1, 10, 0);
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		Money charge = new Money(5000); //$50.00 charge
		
		assertThrows(IllegalArgumentException.class, () -> new ParkingTransaction(entryTime, exitTime, car, charge));
	}
	
	// Testing charge for a compact car 
	/*@Test
	public void testChargeCalculationForCompactCar() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0,
				600, new HourlyRateStrategy());
		ParkingTransaction transaction = new ParkingTransaction(Instant.now(), permit, lot);
		
		assertNotNull(transaction.getCharge()); // validating creation
		assertEquals(true, transaction.getCharge().getCents() > 0);
	}*/
	
	
	// Testing charge for a SUV car 
	/*@Test
	public void testChargeCalculationForSUVCar() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("321 Park Ave", null, "Denver", "CO", "80204"), 100, 0,
				600, new HourlyRateStrategy());
		ParkingTransaction transaction = new ParkingTransaction(Instant.now(), permit, lot);
		
		assertNotNull(transaction.getCharge()); // validating creation
		assertEquals(true, transaction.getCharge().getCents() > 0);
	}*/
	
	
	// TEsting equals and hashcode comparisons
	@Test
    public void testEqualsAndHashCode() {
		LocalDateTime entryTime = LocalDateTime.of(2025, 5, 1, 10, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 5, 1, 15, 0);
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		Money charge = new Money(5000); //$50.00 charge
		
       /* Instant now = Instant.now();
        Car car = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
        ParkingPermit permit = new ParkingPermit(car);
        ParkingLot lot = new ParkingLot("Lot1", new Address("456 Elm St", null, "Cityville", "CA", "90210"), 150, 0,
        		600, new HourlyRateStrategy());*/

        ParkingTransaction transaction1 = new ParkingTransaction(entryTime, exitTime, car, charge);
        ParkingTransaction transaction2 = new ParkingTransaction(entryTime, exitTime, car, charge);
       // ParkingTransaction transaction3 = new ParkingTransaction(Instant.now().plusSeconds(20), permit, lot);

        // Test equality
        assertEquals(transaction1, transaction2);
        //assertNotEquals(transaction1, transaction3);

        // Test hashCode
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
       // assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

	//testing equals with null and a string
   /* @Test
    public void testNotEqualsForNullAndString() {
        Instant now = Instant.now();
        Car car = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
        ParkingPermit permit = new ParkingPermit(car);
        ParkingLot lot = new ParkingLot("Lot1", new Address("456 Elm St", null, "Cityville", "CA", "90210"), 150, 0,
        		600, new HourlyRateStrategy());

        ParkingTransaction transaction = new ParkingTransaction(now, permit, lot);

        assertNotEquals(transaction, null);
        assertNotEquals(transaction, "Test String");
    }*/
}
