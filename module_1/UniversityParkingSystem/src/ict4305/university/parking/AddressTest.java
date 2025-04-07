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
import org.junit.jupiter.api.Test;

class AddressTest {

	// Testing the toString address method
	@Test
	public void testAddressToString() {
		Address address = new Address("1234 Main Ave", "Apt 5", "Denver", "CO", "80204");
		String expected = "1234 Main Ave Apt 5, Denver, CO 80204";
		assertEquals(expected, address.toString());
	}

	
	
	// Testing all of the get address methods
	@Test
	public void testAddressAttributes() {
		Address address = new Address("1234 Main Ave", "Apt 5", "Denver", "CO", "80204");

		assertEquals("1234 Main Ave", address.getStreetAddress1()); // Testing streetAddress1
		assertEquals("Apt 5", address.getStreetAddress2()); // Testing streetAddress2
		assertEquals("Denver", address.getCity()); // Testing city
		assertEquals("CO", address.getState()); // Testing state
		assertEquals("80204", address.getZipCode()); // Testing zip code
		assertEquals("1234 Main Ave Apt 5, Denver, CO 80204", address.getAddressInfo()); // Testing full address
	}
	
	
	
	// Testing constructor with null mandatory fields
	// null streetAddres1
	@Test
	public void testAddressConstructorNullStreetAddress1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address(null, "Apt A", "Denver", "CO", "80204");
		});
		assertEquals("Street address 1 can't be null or empty.", thrown.getMessage());
	}
	
	// null city
	@Test
	public void testAddressConstructorNullCity() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address("1234 Main St", "Apt A", null, "CO", "80204");
		});
		assertEquals("City can't be null or empty.", thrown.getMessage());
	}
	
	// null state
	@Test
	public void testAddressConstructorNullState() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address("1234 Main St", "Apt A", "Denver", null, "80204");
		});
		assertEquals("State can't be null or empty.", thrown.getMessage());
	}
	
	// null zipCode
	@Test
	public void testAddressConstructorNullZipCode() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address("1234 Main St", "Apt A", "Denver", "CO", null);
		});
		assertEquals("Zip code is invalid.", thrown.getMessage());
	}
	
	
	// Testing with empty mandatory fields
	// empty streetAddress1
	@Test
	public void testAddressConstructorEmptyStreetAddress1() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address("", "Apt A", "Denver", "CO", "80204");
		});
		assertEquals("Street address 1 can't be null or empty.", thrown.getMessage());
	}
	
	// empty city
	@Test
	public void testAddressConstructorEmptyCity() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address("1234 Main St", "Apt A", "", "CO", "80204");
		});
		assertEquals("City can't be null or empty.", thrown.getMessage());
	}
	
	// empty state
	@Test
	public void testAddressConstructorEmptyState() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address("1234 Main St", "Apt A", "Denver", "", "80204");
		});
		assertEquals("State can't be null or empty.", thrown.getMessage());
	}
	
	//empty zipCode
	@Test
	public void testAddressConstructorEmptyZipCode() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Address("1234 Main St", "Apt A", "Denver", "CO", "");
		});
		assertEquals("Zip code is invalid.", thrown.getMessage());
	}
	
	
	// Testing equals and HashCode using identical addresses
	@Test
	public void testEqualsAndHashCode() {
		Address address1 = new Address("1234 Main St", "Apt A", "Denver", "CO", "80204");
		Address address2 = new Address("1234 Main St", "Apt A", "Denver", "CO", "80204");
		
		assertEquals(true, address1.equals(address2));
		assertEquals(address1.hashCode(), address2.hashCode());
	}
	
	// Testing HashCode with different addresses
	@Test
	public void testEqualsAndHashCodeDifferentAddess() {
		Address address1 = new Address("1234 Main St", "Apt A", "Denver", "CO", "80204");
		Address address2 = new Address("4321 Main St", "Apt A", "Denver", "CO", "80204");
		
		assertNotEquals(true, address1.equals(address2));
		assertNotEquals(address1.hashCode(), address2.hashCode());
	}
}
