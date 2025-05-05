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
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.Optional;

class ParkingOfficeTest {
	
    // Testing registering a customer
	@Test
	public void testRegisterCustomer() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		ParkingOffice office = new ParkingOffice("University Parking Office", address);
		Customer customer = office.registerCustomer("Sarah Smith", address, "5555551234");
		
		assertNotNull(customer); //Validating customer was created
		assertEquals("Sarah Smith", customer.getName()); // customer name validation
		assertEquals(address, customer.getAddress()); // customer address validation
		assertEquals("5555551234", customer.getPhoneNumber()); // customer phone number validation
		assertEquals(true, office.getCustomers().contains(customer)); //AssertTrue results in compilation error. This is the workaround
	}

	
	// Testing customer registration with null name
	@Test
	public void testRegisterCustomerNullName() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		ParkingOffice office = new ParkingOffice("University Parking Office", address);
	
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			office.registerCustomer(null, address, "5555551234");
		});
		assertEquals("Customer name can't be null or empty.", thrown.getMessage());
	}
	
	
	// Testing registering a car for a customer
	@Test
	public void testRegisterCar() {
		Address address = new Address("321 Main St", "Apt 1", "Denver", "CO", "80204");
		ParkingOffice office = new ParkingOffice("University Parking Office", address);
		Customer customer = office.registerCustomer("John Richards", address, "1235551234");
		Car car = office.registerCar(customer, "ABC123", CarType.COMPACT);
		
		assertNotNull(car); // validating car creation
		assertEquals("ABC123", car.getLicense()); // validating license
		assertEquals(CarType.COMPACT, car.getCarType()); // Validating car type
		assertEquals(customer.getCustomerId(), car.getOwner()); // Validating owner
		assertEquals(true, office.getCars().contains(car)); // Validating car is in list of cars
	}
	
	
	// Test retrieving customer by ID
	@Test
	public void testGetCustomerById() {
		Address address = new Address("321 Main St", "Apt 1", "Denver", "CO", "80204");
		ParkingOffice office = new ParkingOffice("University Parking Office1", address);
		Customer customer = office.registerCustomer("Jane Doe", address, "1235551234");
		
		Optional<Customer> retrievedCustomer = office.getCustomerById(customer.getCustomerId());
		assertEquals(true, retrievedCustomer.isPresent()); // validating customer exists
		assertEquals(customer,retrievedCustomer.get()); // validating customers match when retrieving using customerId
	}
	
	// Test retrieving customerIDs
	@Test
	public void testGetCustomerIds() {
		ParkingOffice office = new ParkingOffice("Office1", new Address("456 Elm St", "", "City", "State", "67867"));
		office.registerCustomer("Jane Smith", new Address("123 Main St", "", "Parker", "CO", "12312"), "9705551234");
		office.registerCustomer("Joe Roberts", new Address("456 Elm St", "", "City", "State", "67867"), "9705554321");
		
		Set<String> customerIds = office.getCustomerIds();
		
		// This checks that there are two customer IDs
		assertEquals(2, customerIds.size());
	}
	
	
	
	// Test retrieving all permit IDs for all cars
	@Test
	public void testGetPermitIds() {
		ParkingOffice office = new ParkingOffice("Office1", new Address("456 Elm St", "", "City", "State", "67867"));
		Customer customer = office.registerCustomer("John Doe", new Address("123 Main St", "", "Parker", "CO", "12312"), "9705551234");
		office.registerCar(customer, "ABC123", CarType.COMPACT);
		office.registerCar(customer, "DEF567", CarType.SUV);
		
		Set<String> permitIds = office.getPermitIds();
		
		// Checking there are two permits
		assertEquals(2, permitIds.size());
	}

	// Test retrieving all permit IDs for all cars
	@Test
	public void testGetPermitIdsForCustomer() {
		ParkingOffice office = new ParkingOffice("Office1", new Address("456 Elm St", "", "City", "State", "67867"));
		Customer customer = office.registerCustomer("John Doe", new Address("123 Main St", "", "Parker", "CO", "12312"), "9705551234");
		office.registerCar(customer, "ABC123", CarType.COMPACT);
		office.registerCar(customer, "DEF567", CarType.SUV);
			
		// creating set of a single customer's permits
		Set<String> permitIds = office.getPermitIds(customer);
			
		// Checking there are two permits
		assertEquals(2, permitIds.size());
		}
	
	
	// testing getting customer names when getting customer by name
	@Test
	public void testGetCustomerByName() {
		Address address = new Address("321 Main St", "Apt 1", "Denver", "CO", "80204");
		ParkingOffice office = new ParkingOffice("University Parking Office1", address);
		Customer customer1 = office.registerCustomer("Jane Doe", address, "1235551234");
		Customer customer2 = office.registerCustomer("Jane Doe", address, "1235554321");
		Customer customer3 = office.registerCustomer("Jane Dow", address, "1235555678");
		
		// getting customers that match customer name
		List<Customer> customers1 = office.getCustomersByName("Jane Doe");
		assertEquals(2,customers1.size());
		assertEquals(true, customers1.contains(customer1));
		assertEquals(true, customers1.contains(customer2));
		assertEquals(false, customers1.contains(customer3));
		
		// getting customers that match customer name
		List<Customer> customers2 = office.getCustomersByName("Jane Dow");
		assertEquals(1,customers2.size());
		assertEquals(false, customers2.contains(customer1));
		assertEquals(false, customers2.contains(customer2));
		assertEquals(true, customers2.contains(customer3));	
	}
	
	//Testing adding a charge
	@Test
	public void testAddCharge() {
		Address address = new Address("321 Main St", "Apt 3", "Denver", "CO", "80204");
		ParkingOffice office = new ParkingOffice("University Parking Office", address);
		Money amount = new Money(1500);
		Instant incurredTime = Instant.now();
		ParkingCharge charge = new ParkingCharge("Permit-0001", "lot-123", incurredTime, amount);
		
		Money result = office.addCharge(charge);
		assertEquals(true, office.getCharges().contains(charge)); // validating charge is in list of charges
		assertEquals(amount,result); // validating amount matches the charge added
	}
	
	// Testing adding a null charge
	@Test
	public void testAddChargeNullCharge() {
		Address address = new Address("321 Main St", "Apt 3", "Denver", "CO", "80204");
		ParkingOffice office = new ParkingOffice("University Parking Office", address);
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			office.addCharge(null);
		});
		assertEquals("Charge can't be null.", thrown.getMessage());
	}
	
	// testing the toString method
	@Test
	public void testToString() {
		ParkingOffice office = new ParkingOffice("Office1", new Address("456 Elm St", "", "City", "State", "67867"));
		String expected = "ParkingOffice--[name='Office1', address=456 Elm St , City, State 67867, customers=[], cars=[], lots=[], charges=[]]";
		assertEquals(expected, office.toString());
	}
	
	
	// Testing equals and HashCode comparisons
	@Test
	public void testEqualsAndHashCode() {
		Address address1 = new Address("321 Main St", "Apt 3", "Denver", "CO", "80204");
		Address address2 = new Address("321 Main St", "Apt 3", "Denver", "CO", "80204");
		Address address3 = new Address("322 Main St", "Apt 3", "Denver", "CO", "80204");
		ParkingOffice office1 = new ParkingOffice("University Parking Office1", address1);
		ParkingOffice office2 = new ParkingOffice("University Parking Office1", address2);
		ParkingOffice office3 = new ParkingOffice("University Parking Office2", address3);
		
		//Testing Equals
		assertEquals(office1, office2);
		assertNotEquals(office1, office3);
		
		//Testing HashCode
		assertEquals(office1.hashCode(), office2.hashCode());
		assertNotEquals(office1.hashCode(), office3.hashCode());
	}
	
	// testing equals with null and a string
	@Test
	public void testEqualsForNullAndString() {
		Address address1 = new Address("322 Main St", "Apt 3", "Denver", "CO", "80204");
		ParkingOffice office1 = new ParkingOffice("University Parking Office1", address1);
		
		assertNotEquals(office1, null);
		assertNotEquals(office1, "HOME");
	}
	
}
