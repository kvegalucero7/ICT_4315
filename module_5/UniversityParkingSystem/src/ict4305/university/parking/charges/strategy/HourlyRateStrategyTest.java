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

import ict4305.university.parking.Address;
import ict4305.university.parking.Car;
import ict4305.university.parking.CarType;
import ict4305.university.parking.Money;
import ict4305.university.parking.ParkingLot;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HourlyRateStrategyTest {

	// Testing for an hourly charge with the following scenario:
	// Hourly parking from 9AM on Tuesday April 1, 2025 to 12:30PM on Tuesday April 1, 2025
	// Due to hours, there is a surcharge of 25% and the compact car is a 20% discount
	@Test
	public void testHourlyChargeForCompactCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 500, new HourlyRateStrategy());
		
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0); // Entry at 9AM
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 12, 30); // Exit at 12:30PM
		Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);
		
		assertEquals("$17.25", charge.toString());
		}
	
	// Testing for an hourly charge with the following scenario:
	// Hourly parking from 9AM on Tuesday April 1, 2025 to 12:30PM on Tuesday April 1, 2025
	// Due to hours, there is a surcharge of 25% and no compact car discount
	@Test
	void testHourlyChargeForSUVCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 500, new HourlyRateStrategy());
		
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0); // Entry at 9A
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 12, 30); // Exit at 12:30PM
		Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");
			
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);
			
		assertEquals("$21.25", charge.toString());
		}
		
	// Testing for an hourly charge with a compact car with overnight
	// Testing for an hourly charge with the following scenario:
	// Hourly parking from 5:40AM on Tuesday April 1, 2025 to 9:10AM on Tuesday April 1, 2025
	// Due to hours, customer would be charged overnight fee and the compact car is a 20% discount
	@Test
	public void testHourlyOvernightChargeForCompactCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 500, new HourlyRateStrategy());
			
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 5, 40); // Entry at 5:40AM
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 9, 10); // Exit at 9:10AM
		Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
			
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);
			
		assertEquals("$32.25", charge.toString());
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
	
	// Testing for an hourly charge with the following scenario:
	// Hourly parking from 10:00AM on Saturday April 5, 2025 to 2:30PM on Saturday April 5, 2025
	// Due to hours would see a 25% surcharge and customer would receive a 20% discount for the  
	// weekend as well as the 20% discount for a compact car
	@Test
	public void testHourlyChargeForCompactDuringWeekend() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 600, new HourlyRateStrategy());

	    LocalDateTime entryTime = LocalDateTime.of(2025, 4, 5, 10, 0); // Saturday entry at 10 AM
	    LocalDateTime exitTime = LocalDateTime.of(2025, 4, 5, 14, 30); // Exit at 2:30 PM
	    Car suv = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");

	    Money charge = lot.calculateParkingCharge(entryTime, exitTime, suv);

	    assertEquals("$20.70", charge.toString());
		}
	
	
	// Testing for a weekend discount using and SUV
	// Testing for an hourly charge with the following scenario:
	// Hourly parking from 10:00AM on Saturday April 5, 2025 to 2:30PM on Saturday April 5, 2025
	// Due to hours would see a 25% surcharge and customer would receive a 20% discount for the  
	// weekend with no compact car discount
	@Test
	public void testHourlyChargeForSUVDuringWeekend() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 600, new HourlyRateStrategy());

	     LocalDateTime entryTime = LocalDateTime.of(2025, 4, 5, 10, 0); // Saturday entry at 10 AM
	     LocalDateTime exitTime = LocalDateTime.of(2025, 4, 5, 14, 30); // Exit at 2:30 PM
	     Car suv = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");

		 Money charge = lot.calculateParkingCharge(entryTime, exitTime, suv);

	     assertEquals("$25.50", charge.toString());
	     }


	// Testing for an hourly charge with the following scenario:
	// Hourly parking on Graduation day 2:00PM on Friday May 16, 2025 to 6:00PM on Friday May 16, 2025
	// Due to hours would see a 25% surcharge for time of day. Customer would receive a 50% surcharge for   
	// Graduation as well as the 20% discount for a compact car
	@Test
	public void testHourlyChargeOnGraduationDay() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 700, new HourlyRateStrategy());

	     LocalDateTime entryTime = LocalDateTime.of(2025, 5, 16, 14, 0); // Graduation day entry at 2 PM
	     LocalDateTime exitTime = LocalDateTime.of(2025, 5, 16, 18, 0); // Exit at 6 PM
	     Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");

		Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);

	     assertEquals("$27.65", charge.toString());
	     }
	
	
	// Testing for an hourly charge with the following scenario:
	// Hourly parking on Home coming day 2:00PM on Saturday October 11, 2025 to 6:00PM on Saturday October 11, 2025
	// Due to hours would see a 25% surcharge for time of day. Customer would receive a 25% surcharge for   
	// Home coming with no compact car discount
	@Test
	public void testHourlyChargeOnHomeComingDay() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 700, new HourlyRateStrategy());

	     LocalDateTime entryTime = LocalDateTime.of(2025, 10, 11, 14, 0); // Graduation day entry at 2 PM
	     LocalDateTime exitTime = LocalDateTime.of(2025, 10, 11, 18, 0); // Exit at 6 PM
	     Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");

	     Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);

	     assertEquals("$21.07", charge.toString());
	     }
	}
