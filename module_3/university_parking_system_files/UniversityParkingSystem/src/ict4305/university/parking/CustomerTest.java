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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.util.Collections;


class CustomerTest {
	
	//Testing constructor with valid inputs
	@Test
	public void testCustomerConstructor() {
		Address address = new Address("1234 Main Ave", "Apt 5", "Denver", "CO", "80204");
		Customer customer = new Customer("CI0001","Fatima Hernandez", address, "1235554567" );
		
		assertNotNull(customer); // Testing for creation
		assertEquals("CI0001", customer.getCustomerId()); // Testing customerId
		assertEquals("Fatima Hernandez", customer.getName()); // Testing name
		assertEquals(address, customer.getAddress()); // Testing customer address
		assertEquals("1235554567", customer.getPhoneNumber()); // Testing customer phone number
	}
	
	
	
	// Testing constructor with null inputs
	// Null customerId
	@Test
	public void testCustomerConstructorNullCustomerId() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Customer(null,"Johnny Appleseed", address, "5555551234");
		});
		assertEquals("Customer ID can't be null or empty.", thrown.getMessage());
	}
	
	// Null customer name
	@Test
	public void testCustomerConstructorNullName() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Customer("CI0001",null, address, "5555551234");
		});
		assertEquals("Customer name can't be null or empty.", thrown.getMessage());
	}
	
	// Null customer address
	@Test
	public void testCustomerConstructorNullAddress() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Customer("CI0001","Johnny Appleseed", null, "5555551234");
		});
		assertEquals("Customer address can't be null.", thrown.getMessage());
	}
	
	// Null customer phoneNumber
	@Test
	public void testCustomerConstructorNullPhoneNumber() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Customer("CI0001","Johnny Appleseed", address, null);
		});
		assertEquals("Customer phone number is invalid.", thrown.getMessage());
	}
	
	
	
	// Testing empty inputs
	// empty customer Id
	@Test
	public void testCustomerConstructorEmptyCustomerId() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Customer("","Johnny Appleseed", address, "5555551234");
		});
		assertEquals("Customer ID can't be null or empty.", thrown.getMessage());
	}
	
	// empty name
	@Test
	public void testCustomerConstructorEmptyName() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Customer("CI0004","", address, "5555551234");
		});
		assertEquals("Customer name can't be null or empty.", thrown.getMessage());
		
	}
	
	// empty customer phoneNumber
	@Test
	public void testCustomerConstructorEmptyPhoneNumber() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Customer("CI0001","Johnny Appleseed", address, "");
		});
		assertEquals("Customer phone number is invalid.", thrown.getMessage());
	}
	
	
	
	// Testing registering a car
	@Test
	public void testCustomerRegistration(){
		Address address = new Address("1234 Main Ave", "Apt 5", "Denver", "CO", "80204");
		Customer customer = new Customer("CI0001","Fatima Hernandez", address, "1235554567");
		Car car = customer.registerCar("123ABC", CarType.COMPACT);
		
		assertNotNull(car); //Testing for creation
		assertEquals(1,customer.getCars().size()); // Testing adding to list
		assertEquals(true, customer.getCars().contains(car)); // Validating item is in list
	}
	
	// Testing empty customer license in customer registration
	@Test
	public void testCustomerRegistrationEmptyLicense() {
		Address address = new Address("1234 Main Ave", "Apt 5", "Denver", "CO", "80204");
		Customer customer = new Customer("CI0001", "Fatima Hernandez", address, "1235554567");
		Executable executable = ()-> customer.registerCar("", CarType.COMPACT);
		Exception exception = assertThrows(IllegalArgumentException.class, executable);
		
		assertEquals("License can't be null or empty.", exception.getMessage());
	}

	// Testing the toString method
	@Test
	public void testCustomerToString() {
		Address address = new Address("1234 Main Ave", "Apt 5", "Denver", "CO", "80204");
		Customer customer = new Customer("CI0001", "Fatima Hernandez", address, "1235554567");
		
		String expected = "Customer--[customerId=CI0001, name=Fatima Hernandez, address=" 
		                  + address.toString() + ", phoneNumber=1235554567, cars=[]]"; 
		assertEquals(expected,customer.toString());
	}
	
	// Testing the registerCar method
	@Test
	public void testRegisterCar() {
		Address address = new Address("1234 Main Ave", "Apt 5", "Denver", "CO", "80204");
		Customer customer = new Customer("CI0001","Fatima Hernandez", address, "1235554567");
		Car car = customer.registerCar("123ABC", CarType.COMPACT);
		
		assertEquals(1, customer.getCars().size()); // Validation customer has a car registered
		assertEquals("123ABC", car.getLicense()); // Validating the license on the car
		assertEquals(CarType.COMPACT, car.getCarType()); // Validating the carType that was registered
	}
	
	// Testing a null License during car registration
	@Test
	public void testRegisterCarNullLicense() {
		Address address = new Address("1234 Main Ave", "Apt A", "Denver", "CO", "80204");
		Customer customer = new Customer("CI0001","Fatima Hernandez", address, "1235554567");
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			customer.registerCar(null, CarType.COMPACT);
		});
		assertEquals("License can't be null or empty.", thrown.getMessage());
	}
	
	
	// Testing equals and HashCode
	@Test
	public void testEqualsAndHashCode() {
		Address address = new Address("321 Main Ave", "Apt A", "Denver", "CO", "80204");
		Customer customer1 = new Customer("CI0001","Fatima Hernandez", address, "1235554567");
		Customer customer2 = new Customer("CI0001","Fatima Hernandez", address, "1235554567");
		
		assertEquals(true, customer1.equals(customer2));
		assertEquals(customer1.hashCode(),customer2.hashCode());
	}
	
}
