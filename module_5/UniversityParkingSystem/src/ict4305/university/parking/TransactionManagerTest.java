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
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import ict4305.university.parking.charges.strategy.HourlyRateStrategy;
import java.time.LocalDate;


class TransactionManagerTest {
	
	@Test
	public void testPostTransactionSuccessfully() {
		// initial set up
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		TransactionManager transactionManager = new TransactionManager();
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 11, 30);
		
		// posting the transaction
		transactionManager.postTransaction(parkingLot, entryTime, exitTime, car);
		
		// comparing to size one would mean it is the only transaction recorded
		assertEquals(1, transactionManager.getTransactions().size(),"Transaction should be recorded.");
	}
	
	@Test
	public void testChargeCalculationForSingleTransaction() {
		// initial set up
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		TransactionManager transactionManager = new TransactionManager();
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 11, 30);
		
		// recording a single transaction
		transactionManager.postTransaction(parkingLot, entryTime, exitTime, car);
		
		//setting up expected value
		Money expectedCharge = new HourlyRateStrategy().calculateCharge(new Money(600), entryTime, exitTime, car);
		
		//testing expected against actual
		assertEquals(expectedCharge, transactionManager.calculateCharges(), "Charge shouldmatch expected calculation.");
	}
	
	@Test
	public void testCustomerChargeCalculation() {
		// the initial set up
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		TransactionManager transactionManager = new TransactionManager();
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 11, 30);
		
		// creating the original transaction
		transactionManager.postTransaction(parkingLot, entryTime, exitTime, car);
		
		// creating the second transaction 
		LocalDateTime secondEntryTime = LocalDateTime.of(2025, 4, 2, 9, 0);
		LocalDateTime secondExitTime = LocalDateTime.of(2025, 4, 2, 11, 30);
		
		// adding the second transaction
		transactionManager.postTransaction(parkingLot, secondEntryTime, secondExitTime, car);

		// creating the expected values
		Money expectedCharge = new Money(0);
		expectedCharge = expectedCharge.add(new HourlyRateStrategy().calculateCharge(new Money(600), entryTime, exitTime, car));
		expectedCharge = expectedCharge.add(new HourlyRateStrategy().calculateCharge(new Money(600), secondEntryTime, secondExitTime, car));

		Customer customer = new Customer("CI0005", "Johnny Smith", new Address("998 A Street", null, "Denver", "CO", "80204"), "1235551234");
		
		//comparing the expected with the actual
		assertEquals(expectedCharge, transactionManager.calculateChargeForCustomer(customer),"Charge should match customer's total.");
	}
	
	@Test
	public void testEqualsAndHashCodeForTransactionManager() {
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		TransactionManager transactionManager = new TransactionManager();
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 11, 30);
		
		// creating the original
		transactionManager.postTransaction(parkingLot, entryTime, exitTime, car);
		
		// creating the duplicate
		TransactionManager transactionManager2 = new TransactionManager();
		transactionManager2.postTransaction(parkingLot, entryTime, exitTime, car);
		
		// testing the equals and the hash code
		assertEquals(transactionManager, transactionManager2, "Transaction Mangers with the same values should be equal.");
		assertEquals(transactionManager.hashCode(),transactionManager2.hashCode(), "Hash Codes should match.");
	}
	
	@Test
	public void testInvalidTransactionThrowsException() {
		ParkingLot parkingLot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0, 
				600, new HourlyRateStrategy());
		TransactionManager transactionManager = new TransactionManager();
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0);
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 11, 30);
		
		// null parkingLot
		assertThrows(IllegalArgumentException.class, () -> transactionManager.postTransaction(null, entryTime, exitTime, car));
		
		// null entryTime
		assertThrows(IllegalArgumentException.class, () -> transactionManager.postTransaction(parkingLot, null, exitTime, car));
		
		// null exitTime
		assertThrows(IllegalArgumentException.class, () -> transactionManager.postTransaction(parkingLot, entryTime, null, car));
		
		// null car
		assertThrows(IllegalArgumentException.class, () -> transactionManager.postTransaction(parkingLot, entryTime, exitTime, null));
	}
}
