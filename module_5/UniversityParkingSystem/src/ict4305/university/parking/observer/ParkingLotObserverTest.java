/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.observer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import ict4305.university.parking.*;
import ict4305.university.parking.charges.strategy.HourlyRateStrategy;
import ict4305.university.parking.events.ParkingEvent;

class ParkingLotObserverTest {

	@Test
	public void testObserverHandlesExitEvent() {
		// initial set up
		TransactionManager transactionManager = new TransactionManager();
		ParkingLotObserver observer = new ParkingLotObserver(transactionManager);
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 1, 
				600, new HourlyRateStrategy());
		LocalDateTime timestamp = LocalDateTime.now();
		
		// recording initial capacity
		int initialUsage = parkingLot.getCurrentUse();
		
		// vehicle exits
		ParkingEvent exitEvent = new ParkingEvent(timestamp, permit, parkingLot, false);
		observer.update(exitEvent);
		
		// capacity should go down when vehicle exits
		assertEquals(initialUsage - 1, parkingLot.getCurrentUse(), "Parking lot usage should decrease when a car exits.");
		assertEquals(1, transactionManager.getTransactions().size(), "Transaction should be recorded efter an exit.");
	}
	
	@Test
	public void testObserverHandlesEntryEvent() {
		// initial set up
		TransactionManager transactionManager = new TransactionManager();
		ParkingLotObserver observer = new ParkingLotObserver(transactionManager);
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		LocalDateTime timestamp = LocalDateTime.now();
		
		//recording initial capacity
		int initialUsage = parkingLot.getCurrentUse();
		
		// updating value
		ParkingEvent trueEvent = new ParkingEvent(timestamp, permit, parkingLot, true);
		observer.update(trueEvent);
		
		// comparing updated values should be increased as car goes in
		assertEquals(initialUsage + 1, parkingLot.getCurrentUse(), "Parking lot usage should increase when a car goes in.");
		assertEquals(0, transactionManager.getTransactions().size(), "Transaction should not trigger a transaction.");
	}
	
	@Test
	public void testObserveIgnoresEntryEvent() {
		TransactionManager transactionManager = new TransactionManager();
		ParkingLotObserver observer = new ParkingLotObserver(transactionManager);
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		LocalDateTime timestamp = LocalDateTime.now();
		
		ParkingEvent entryEvent = new ParkingEvent(timestamp, permit, parkingLot, true);
		
		observer.update(entryEvent);
		assertEquals(0, transactionManager.getTransactions().size(), "Entry event should not trigger a transaction.");
	}
	
	@Test
	public void testAddObserveReceivesUpdates() {
		// itinial set up
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		TransactionManager transactionManager = new TransactionManager();
		ParkingLotObserver observer = new ParkingLotObserver(transactionManager);
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		LocalDateTime timestamp = LocalDateTime.now();

		parkingLot.addObserver(observer); // adding an observer
		
		ParkingPermit permit = new ParkingPermit(car);
		ParkingEvent entryEvent = new ParkingEvent(timestamp, permit, parkingLot, true);

		//simulating and event notification
		parkingLot.notifyObservers(entryEvent);
		
		assertEquals(1, parkingLot.getCurrentUse(), "Lot usage should increase when notified of entry.");
	}
	
	@Test
	public void testRemoveObserveStopUpdates() {
		// initial set up
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		TransactionManager transactionManager = new TransactionManager();
		ParkingLotObserver observer = new ParkingLotObserver(transactionManager);
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		LocalDateTime timestamp = LocalDateTime.now();
		
		parkingLot.addObserver(observer); //Adding observer
		parkingLot.removeObserver(observer); // Removing Observer
		
		ParkingPermit permit = new ParkingPermit(car);
		ParkingEvent exitEvent = new ParkingEvent(timestamp, permit, parkingLot, false);
		
		// Simulating an event notification
		parkingLot.notifyObservers(exitEvent);

		assertEquals(0, transactionManager.getTransactions().size(), "Transaction should not be recorded after observer removal.");
	}

}
