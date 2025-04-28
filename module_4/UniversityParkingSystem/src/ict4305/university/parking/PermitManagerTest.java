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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class PermitManagerTest {

	// testing customer registration
	@Test
	public void testRegisterCustomer() {
		PermitManager manager = new PermitManager();
		Customer customer = new Customer("Cust123","Jane Doe", new Address("432 Main Ave", null, "City", "CO", "80204"), "1234567890");
		manager.registerCustomer(customer);
		
		assertEquals(true, manager.getRegisteredCustomers().contains(customer));
	}
	
	// testing duplicate customer
	@Test
	public void testRegisterDuplicateCustomerThrowsException() {
		PermitManager manager = new PermitManager();
		Customer customer = new Customer("Cust123","Jane Doe", new Address("432 Main Ave", null, "City", "CO", "80204"), "1234567890");
		manager.registerCustomer(customer);
		
		assertThrows(IllegalArgumentException.class, () -> manager.registerCustomer(customer));
	}
	
	// Testing car registration
	@Test
	public void testRegisterCar() {
		PermitManager manager = new PermitManager();
		Car car = new Car("Permit 1233", LocalDate.now().plusYears(1),"XYZ123", CarType.COMPACT, "CI0005");
		ParkingPermit permit = manager.registerCar(car);
		
		assertNotNull(permit);
		assertEquals(true, manager.getRegisteredPermits().contains(permit));
	}
	
	// testing duplicate car registration
	@Test
	public void testRegisterDuplicateCarThrowsException() {
		PermitManager manager = new PermitManager();
		Car car = new Car("Permit 1233", LocalDate.now().plusYears(1),"XYZ123", CarType.COMPACT, "CI0005");
		manager.registerCar(car);
		
		assertThrows(IllegalArgumentException.class, () -> manager.registerCar(car));
	}
	
	// Testing getting all permit ids for a customer
	@Test
	public void testGetPermitIdsForCustomer() {
		PermitManager manager = new PermitManager();
		Customer customer = new Customer("CI0005","Jane Doe", new Address("432 Main Ave", null, "City", "CO", "80204"), "1234567890");
		manager.registerCustomer(customer);
		
		Car car1 = new Car("Permit 1233", LocalDate.now().plusYears(1),"XYZ123", CarType.COMPACT, "CI0005");
		Car car2 = new Car("Permit 5678", LocalDate.now().plusYears(1),"ABC123", CarType.SUV, "CI0005");

		manager.registerCar(car1);
		manager.registerCar(car2);
		
		List<String> permitIds = manager.getPermitIdsForCustomer(customer);
		assertEquals(2, permitIds.size());
		assertEquals(true, permitIds.contains("Permit 1233"));
		assertEquals(true, permitIds.contains("Permit 5678"));
	}
	
	// Testing removing a customer
	@Test
	public void testRemoveCustomer() {
		//creating
		PermitManager manager = new PermitManager();
		Customer customer1 = new Customer("CI0010","Jane Doe", new Address("432 Main Ave", null, "City", "CO", "80204"), "1234567890");
		manager.registerCustomer(customer1);
		
		Car car2 = new Car("Permit 5678", LocalDate.now().plusYears(1),"ABC123", CarType.SUV, "CI0010");
		manager.registerCar(car2);
		
		//removing
		manager.removeCustomer("CI0010");
		assertEquals(false, manager.isCustomerRegistered("CI0010"));
		assertEquals(0, manager.getRegisteredPermits().size());
	}
	
	// Testing getting total customers and permits
	@Test
	public void testStatistics() {
		PermitManager manager = new PermitManager();
		Customer customer1 = new Customer("CI0005","Jane Doe", new Address("432 Main Ave", null, "City", "CO", "80204"), "1234567890");
		Customer customer2 = new Customer("CI0010","Joe Smith", new Address("123 Main St", null, "Denver", "CO", "80204"), "1234567899");
		
		manager.registerCustomer(customer1);
		manager.registerCustomer(customer2);

		Car car1 = new Car("Permit 1233", LocalDate.now().plusYears(1),"XYZ123", CarType.COMPACT, "CI0005");
		Car car2 = new Car("Permit 5678", LocalDate.now().plusYears(1),"ABC123", CarType.SUV, "CI0010");

		manager.registerCar(car1);
		manager.registerCar(car2);
		
		Map<String, Integer> stats = manager.getStatistics();
		assertEquals(2, stats.get("Total Customers"));
		assertEquals(2,stats.get("Total Permits"));
	}
	
	// testing equals and Hashcode comparisions
	 @Test
	    public void testEqualsAndHashCode() {
	        PermitManager manager1 = new PermitManager();
	        PermitManager manager2 = new PermitManager();

	        // Test equality for empty managers
	        assertEquals(manager1, manager2);
	        assertEquals(manager1.hashCode(), manager2.hashCode());

	        // Add a customer to one manager
			Customer customer = new Customer("CI0010","Joe Smith", new Address("123 Main St", null, "Denver", "CO", "80204"), "1234567899");
	        manager1.registerCustomer(customer);

	        assertNotEquals(manager1, manager2);
	    }

	 // Testing equals with null and string
	    @Test
	    public void testNotEqualsForNullAndString() {
	        PermitManager manager = new PermitManager();
	        assertNotEquals(manager, null);
	        assertNotEquals(manager, "permit string");
	    }


}
