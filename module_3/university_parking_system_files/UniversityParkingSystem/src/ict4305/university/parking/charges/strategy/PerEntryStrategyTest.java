package ict4305.university.parking.charges.strategy;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import ict4305.university.parking.Address;
import ict4305.university.parking.Car;
import ict4305.university.parking.CarType;
import ict4305.university.parking.Money;
import ict4305.university.parking.ParkingLot;

class PerEntryStrategyTest {

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
		
	
	// Testing for an entry charge with an SUV// Testing per entry charge with the following scenario:
	// Parking from 9AM on Tuesday April 1, 2025 to 12:30PM on Tuesday April 1, 2025
	// Due to hours, there is a surcharge of 25% with no compact car discount
	@Test
	public void testPerEntryChargeForSUVCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
	    ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 1200, new PerEntryStrategy());
			
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 9, 0); // Entry at 9A
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 1, 12, 30); // Exit at 12:30PM
		Car carSUV = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");
				
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, carSUV);
				
		assertEquals("$12.00", charge.toString());
	}
		
			
	
	// Testing per entry charge with the following scenario:
	// Parking from 5:40AM on Tuesday April 1, 2025 to 9:10PM on Wednesday April 2, 2025
	// There is a $15.00 fee for overnight stays and the compact car is a 20% discount
	@Test
	public void testPerEntryOvernightChargeForCompactCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 1200, new PerEntryStrategy());
				
		LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 5, 40); // Entry at 5:40AM
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 2, 9, 10); // Exit at 9:10AM The following Day
		Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
				
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);
				
		assertEquals("$24.60", charge.toString());
	}	
		
	
	// Testing per entry charge with the following scenario:		
	// Parking from 5:40AM on Tuesday April 1, 2025 to 9:10PM on Wednesday April 2, 2025
	// There is a $15.00 fee for overnight stays and no compact car discount
	@Test
	public void testPerEntryOvernightChargeForSUVCar() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
	    ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 1200, new PerEntryStrategy());
				
	    LocalDateTime entryTime = LocalDateTime.of(2025, 4, 1, 5, 40); // Entry at 5:40AM
		LocalDateTime exitTime = LocalDateTime.of(2025, 4, 2, 2, 10); // Exit at 2:10AM the following day
		Car carSUV = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");
					
		Money charge = lot.calculateParkingCharge(entryTime, exitTime, carSUV);
					
		assertEquals("$27.00", charge.toString());
	}
		
		
	// Testing per entry charge with the following scenario:
	// Parking from 10:00AM on Saturday April 5, 2025 to 2:30PM on Saturday April 2, 2025
	// There is a 20% discount for weekend use and the compact car is a 20% discount
	@Test
	public void testPerEntryChargeForCompactDuringWeekend() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
	    ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 1200, new PerEntryStrategy());

	    LocalDateTime entryTime = LocalDateTime.of(2025, 4, 5, 10, 0); // Saturday entry at 10 AM
	    LocalDateTime exitTime = LocalDateTime.of(2025, 4, 5, 14, 30); // Exit at 2:30 PM
	    Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");

	    Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);

	    assertEquals("$7.68", charge.toString());
	}
		
		
	// Testing per entry charge with the following scenario:
	// Parking from 10:00AM on Saturday April 5, 2025 to 2:30PM on Saturday April 2, 2025
	// There is a 20% discount for weekend use and there is no compact car discount
	@Test
	public void testPerEntryChargeForSUVDuringWeekend() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
	    ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 1200, new PerEntryStrategy());

	    LocalDateTime entryTime = LocalDateTime.of(2025, 4, 5, 10, 0); // Saturday entry at 10 AM
	    LocalDateTime exitTime = LocalDateTime.of(2025, 4, 5, 14, 30); // Exit at 2:30 PM
	    Car carSUV = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.SUV, "CI00001");

	    Money charge = lot.calculateParkingCharge(entryTime, exitTime, carSUV);
	    
	    assertEquals("$9.60", charge.toString());
	}
		
	// Testing for an entry charge with a compact car with overnight
	// Testing per entry charge with the following scenario:
	// Graduation parking from 2:00PM on Friday May 16, 2025 to 6:00PM on Friday May 16, 2025
	// There is a 50% surcharge for graduation and the compact car is a 20% discount
	@Test
	public void testPerEntryChargeOnGraduationDay() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 700, new PerEntryStrategy());

		LocalDateTime entryTime = LocalDateTime.of(2025, 5, 16, 14, 0); // Graduation day entry at 2 PM
	    LocalDateTime exitTime = LocalDateTime.of(2025, 5, 16, 18, 0); // Exit at 6 PM
	    Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");

	    Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);

	    assertEquals("$9.10", charge.toString());
	}
		     
		

	// Testing per entry charge with the following scenario:
	// Home Coming parking from 2:00PM on Saturday October 11, 2025 to 6:00PM on Saturday October 11, 2025
	// There is a 25% surcharge forhomecoming and there is a 20% compact car discount
	@Test
	public void testPerEntryChargeOnHomeComingDay() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
	    ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 700, new PerEntryStrategy());

	    LocalDateTime entryTime = LocalDateTime.of(2025, 10, 11, 14, 0); // Graduation day entry at 2 PM
	    LocalDateTime exitTime = LocalDateTime.of(2025, 10, 11, 18, 0); // Exit at 6 PM
	    Car compactCar = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");

	    Money charge = lot.calculateParkingCharge(entryTime, exitTime, compactCar);

	    assertEquals("$5.88", charge.toString());
	}
}