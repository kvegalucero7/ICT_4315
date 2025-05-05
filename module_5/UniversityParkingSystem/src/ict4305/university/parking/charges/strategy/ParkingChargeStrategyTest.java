/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.charges.strategy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import ict4305.university.parking.Money;
import ict4305.university.parking.ParkingLot;
import ict4305.university.parking.Address;
import ict4305.university.parking.Car;
import ict4305.university.parking.CarType;

class ParkingChargeStrategyTest {

	// Testing per entry charge with the following scenario:
	// Parking from 9AM on Tuesday April 1, 2025 to 12:30PM on Tuesday April 1, 2025
	// Due to hours, there is a surcharge of 25% and the compact car is a 20% discount
	@Test
	public void testPerEntryChargeForCompactCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
	    ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 1200, new PerEntryStrategy());
				
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0); // Entry at 9AM
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 12, 30); // Exit at 12:30PM
		Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
				
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);
				
		assertEquals("$9.60", charge.toString());
	}
	
	// Testing for an hourly charge with the following scenario:
	// Hourly parking from 5:40AM on Tuesday April 1, 2025 to 9:10AM on Tuesday April 1, 2025
	// Due to hours, customer would be charged overnight fee and there would be no compact discount
	@Test
	public void testHourlyOvernightChargeForSUVCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 500, new HourlyRateStrategy());
					
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 5, 40); // Entry at 5:40AM
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 9, 10); // Exit at 9:10AM
		Car carSUV = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");
					
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, carSUV);
					
		assertEquals("$36.25", charge.toString());
	}
}
