/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.events;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import ict4305.university.parking.*;
import ict4305.university.parking.charges.strategy.HourlyRateStrategy;

class ParkingEventTest {

	@Test
	public void testCreateEntryEvent() {
		// initial set up
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		LocalDateTime timestamp = LocalDateTime.now();
		
		// creating entry
		ParkingEvent entryEvent = new ParkingEvent(timestamp, permit, parkingLot, true);
		
		// verifying registered as entry
		assertTrue(entryEvent.isEntry(), "Event should be marked as an entry.");
		
		// comparing the permits in question
		assertEquals(permit.getCar(), entryEvent.getPermit().getCar(), "Car in permit should match.");
		
		// comparing the parking lots
		assertEquals(parkingLot, entryEvent.getParkingLot(), "Parking lot should match.");
	}
	
	@Test
	public void testCreateExitEvent() {
		// initial set up
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		LocalDateTime timestamp = LocalDateTime.now();
		
		// creating exit
		ParkingEvent exitEvent = new ParkingEvent(timestamp, permit, parkingLot, false);
		
		// verifying exit recorded
		assertFalse(exitEvent.isEntry(), "Event should be marked as an exit.");
		
		// comparing permits
		assertEquals(permit.getCar(), exitEvent.getPermit().getCar(), "Car in permit should match.");
		
		// comparing parking lots
		assertEquals(parkingLot, exitEvent.getParkingLot(), "Parking lot should match.");
	}

}
