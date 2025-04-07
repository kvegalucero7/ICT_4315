/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 1 Parking System (Continuation of ICT 4305)
 * April 6, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionManagerTest {

	// Testing a successful transaction creation
	@Test
	public void testProcessParkingTransactionSuccessful() {
		TransactionManager manager = new TransactionManager();
		
		//Setting up  a car, the permit and a parking lot
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0);
		
		//Processing a transaction
		ParkingTransaction transaction = manager.processParking(Instant.now(), permit, lot);
		
		//Validation a valid transaction
		assertNotNull(transaction); // validating transaction existence
		assertEquals(true, manager.getTransactions().contains(transaction)); // list has transaction
		assertEquals(permit, transaction.getPermit()); // validating permit used
		assertEquals(lot, transaction.getLot()); // validating lot involved
		assertNotNull(transaction.getCharge()); // validating charge
		assertEquals(true, transaction.getCharge().getCents() > 0); // validating amount charged
	}
	
	// Testing total charges for permit
	@Test
	public void testCalculateChargesForPermit() {
		TransactionManager manager = new TransactionManager();
		
		//Setting up  a car, the permit and a parking lot
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0);
		
		//Processing a transaction
		manager.processParking(Instant.now(), permit, lot);
		
		//Calculate total charges for permit
		Money charges = manager.calculateCharges(permit);
		
		//Validate the charges
		assertNotNull(charges);
		assertEquals(true, charges.getCents() > 0);
	}
	
	// Testing totals charges for customer
	@Test
	public void testCalculateChargesForCustomer() {
		TransactionManager manager = new TransactionManager();
		
		//Setting up a customer,car, the permit and a parking lot
		Customer customer = new Customer("CI0006", "Johnny Smith", new Address("998 A Street", null, "Denver", "CO", "80204"), "1235551234");
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0006");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0);
		
		//Processing a transaction
		manager.processParking(Instant.now(), permit, lot);
				
		//Calculate total charges for customer
		Money charges = manager.calculateChargesForCustomer(customer);
		
		//Validating customer charges
		assertNotNull(charges);
		assertEquals(true, charges.getCents() > 0);
	}
	
	
	// Testing new customer has no charges
	@Test
	public void testNoChargeForNewCustomer() {
		TransactionManager manager = new TransactionManager();
		
		// New Customer
		Customer customer = new Customer("CI0006", "Johnny Smith", new Address("998 A Street", null, "Denver", "CO", "80204"), "1235551234");
		//Calculate total charges for customer
		Money charges = manager.calculateChargesForCustomer(customer);
				
		//Validating customer charges
		assertNotNull(charges);
		assertEquals(0, charges.getCents());
	}
	
	
	// Getting all transactions in list
	@Test
	public void testRetrieveAllTransactions() {
		TransactionManager manager = new TransactionManager();
		
		//Setting up  a car, the permit and a parking lot
		Car car = new Car("Permit 1234", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = new ParkingPermit(car);
		ParkingLot lot = new ParkingLot("Lot1", new Address("987 Central Ave", null, "Denver", "CO", "80204"), 100, 0);
		
		//Processing a transactions
		ParkingTransaction transaction1 = manager.processParking(Instant.now(), permit, lot);
		ParkingTransaction transaction2 = manager.processParking(Instant.now(), permit, lot);

		//Retrieving to list
		List<ParkingTransaction> transactions = manager.getTransactions();
		
		//Validating transaction list
		assertEquals(2, transactions.size());
		assertEquals(true, transactions.contains(transaction1));
		assertEquals(true, transactions.contains(transaction2));
	}
	
	// Testing equals and hashCode comparisons
	 @Test
	    public void testEqualsAndHashCode() {
	        TransactionManager manager1 = new TransactionManager();
	        TransactionManager manager2 = new TransactionManager();

	        // Test equality for empty managers
	        assertEquals(manager1, manager2);
	        assertEquals(manager1.hashCode(), manager2.hashCode());

	        // Add a transaction to one manager
	        Car car = new Car("Permit-001", LocalDate.now().plusYears(1), "ABC123", CarType.COMPACT, "CI0001");
	        ParkingPermit permit = new ParkingPermit(car);
	        ParkingLot lot = new ParkingLot("Lot1", new Address("456 Elm St", null, "Cityville", "CA", "90210"), 150, 0);
	        manager1.processParking(Instant.now(), permit, lot);

	        assertNotEquals(manager1, manager2);
	    }

	 // Testing equals using null and string
	 @Test
	 public void testNotEqualsForNullAndString() {
		 TransactionManager manager = new TransactionManager();
	     assertNotEquals(manager, null);
	     assertNotEquals(manager, "Some String");
	 }
}
