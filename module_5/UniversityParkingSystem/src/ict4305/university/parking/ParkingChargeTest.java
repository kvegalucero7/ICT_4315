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
import java.time.Instant;
import org.junit.jupiter.api.Test;

class ParkingChargeTest {

	// Testing setting and getting a parking charge
	@Test
	public void testSetAndGetDetails() {
		Money amount = new Money(1500); //This is $15.00
		Instant incurredTime = Instant.now();
		ParkingCharge charge = new ParkingCharge("Permit-0001", "lot-321", incurredTime, amount);
		
		assertEquals("Permit-0001", charge.getPermitId()); // Checking permitId charged
		assertEquals("lot-321", charge.getLotId()); // Checking Lot assessing charge
		assertEquals(incurredTime, charge.getIncurred()); // Checking when charge was incurred
		assertEquals(amount, charge.getAmount()); // Checking amount incurred
	}
	
	// Testing toString method for parking charge
	@Test
	public void testToString() {
		Money amount = new Money(2000);
		Instant incurredTime = Instant.now();
		ParkingCharge charge = new ParkingCharge("Permit-0002", "Lot-324", incurredTime, amount);
		
		String expected = "ParkingCharge--[permitId=Permit-0002, lotId=Lot-324, incurred=" + incurredTime + ", amount=$20.00]"; // expected value
		assertEquals(expected, charge.toString());
	}
}
