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

class MoneyTest {

	// Testing setting and getting cents
	@Test
	public void testSetAndGetCents() {
		Money money = new Money(500); // Equal to $5.00
		assertEquals(500, money.getCents());
	}
	
	// Testing getting value as a dollar amount
	@Test
	public void testGetDollars() {
		Money money = new Money(12345);
		assertEquals(123.45, money.getDollars(),0.01); //The 0.01 is to allow for small variance for rounding
	}
	
	// Testing the toString method
	@Test
	public void testToString() {
		Money money = new Money(7879);
		assertEquals("$78.79", money.toString());
	}
	
	// Testing exception for negative cent value
	@Test
	public void testNegativeCents() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Money(-50);
		});
		assertEquals("Cents can't be negative.", thrown.getMessage());
	}
	
	// Testing equals and hashCode comparisons
	@Test
	public void testEqualsAndHashCode() {
		Money money1 = new Money(1000); //$10.00
		Money money2 = new Money(1000); //$10.00
		Money money3 = new Money(500); //$5.00
		
		//Testing Equals
		assertEquals(money1, money2);
		assertNotEquals(money1, money3);
		
		//Testing HashCode
		assertEquals(money1.hashCode(), money2.hashCode());
		assertNotEquals(money1.hashCode(), money3.hashCode());
	}

	//Testing comparisons with null and a string
	@Test
	public void testNotEqualsForNullAndString() {
		Money money1 = new Money(1000); //$10.00
		assertNotEquals(money1, null);
		assertNotEquals(money1, "Hello");
	}
}


