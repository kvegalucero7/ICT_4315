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
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import ict4305.university.parking.charges.strategy.HourlyRateStrategy;
import ict4305.university.parking.charges.strategy.PerEntryStrategy;

class ParkingLotTest {
	
	//Testing the constructor with valid inputs
    @Test
	public void testParkingLotConstructor() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 600, new HourlyRateStrategy());
		assertEquals("PL001", lot.getLotId()); // validating lot Id
		assertEquals(address, lot.getAddress()); // Validating lot address
		assertEquals(100, lot.getCapacity()); // validating lot capacity
		assertEquals(5, lot.getCurrentUse()); // validating spots in use
		assertEquals("$6.00", lot.getBaseRate().toString()); // validating the lot base rate
		assertTrue(lot.getChargeStrategy() instanceof HourlyRateStrategy, "Charge strategy should be an instance of  HourlyRateStrategy."); // validating parking lot charging strategy
	}
    
	//Test constructor with negative capacity. This should throw an Illegal Exception
	@Test
	public void testConstructorNegativeCapacity() {
		Address address = new Address("123 Main St","", "City", "State", "12345");
		new ParkingLot("L001", address, 40, 42, 600, new HourlyRateStrategy());
	}
	
	
	// Constructor with a Null lotId
		@Test
		public void testConstructorNullLotId() {
			Address address = new Address("123 Main St","", "City", "State", "12345");

			Exception exception = assertThrows(IllegalArgumentException.class,() ->{
				new ParkingLot(null, address, 100, 5, 600, new PerEntryStrategy());
			});
			assertEquals("Lot ID has not been set yet.", exception.getMessage());
		}
		
		
	// Constructor with a Null address
	@Test
	public void testConstructorNullAddress() {
		Exception exception = assertThrows(IllegalArgumentException.class,() ->{
			new ParkingLot("PL001", null, 100, 5, 600, new PerEntryStrategy());
		});
		assertEquals("Address has not been set yet.", exception.getMessage());
	}
	
	
	//Testing the constructor with null parking strategy
    @Test
	public void testNullParkingChargeStrategy() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		;
		
		Exception exception = assertThrows(IllegalArgumentException.class,() ->{
			new ParkingLot("PL001", address, 100, 5, 600, null);
		});
		assertEquals("Charge Strategy has not been set yet.", exception.getMessage());
		
    }
	
	// Testing toString method
	@Test
	public void testParkingLotToString() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 2, 600, new HourlyRateStrategy());
		String expected = "ParkingLot--[lotId=PL001, address=" + address + ", capacity="
				+"100, usage=2]";
		assertEquals(expected, lot.toString());
	}
	
	
	// Testing the equals and HashCode
	// identical lots
	@Test
	public void testEqualsAndHashCodeIdentical() {
		Address address = new Address("123 Main St", "", "City", "State", "12345");
		ParkingLot lot1 =new ParkingLot("L001", address, 50, 10, 600, new HourlyRateStrategy());
		ParkingLot lot2 =new ParkingLot("L001", address, 50, 10, 600, new HourlyRateStrategy());
		
		assertEquals(true, lot1.equals(lot2));
		assertEquals(lot1.hashCode(),lot2.hashCode());
	}
	
	// Distinct lots
	@Test
	public void testEqualsAndHashCodeDifferent() {
		Address address1 = new Address("123 Main St", "", "City", "State", "12345");
		Address address2 = new Address("4321 Main St", "", "Denver", "CO", "80204");

		ParkingLot lot1 =new ParkingLot("L001", address1, 50, 10, 600, new HourlyRateStrategy());
		ParkingLot lot2 =new ParkingLot("L001", address2, 100, 40, 600, new HourlyRateStrategy());
		
		assertEquals(false, lot1.equals(lot2));
		assertNotEquals(lot1.hashCode(),lot2.hashCode());
	}
	
	// Testing parking lot capacity changes and entry and exit times
	@Test
	public void testEntryExit() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 2, 600, new HourlyRateStrategy());
		Car car1 = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		Car car2 = new Car("321CBA", LocalDate.now().plusYears(1), "321CBA", CarType.SUV, "CI00002");

		//Entering Tests
		int updatedCurrentUse1 = lot.entry(car1);
		assertEquals(3,updatedCurrentUse1); // lot capacity
		assertNotNull(lot.getEntryTime(car1)); // entry time
		
		int updatedCurrentUse2 = lot.entry(car2);
		assertEquals(4,updatedCurrentUse2); // lot capacity
		assertNotNull(lot.getEntryTime(car2)); // entry time
		
		//Exiting Tests
		int updatedCurrentUse3 = lot.exit(car1);
		assertEquals(3,updatedCurrentUse3); // lot capacity
		assertNotNull(lot.getExitTime(car1)); // exit time
		
		int updatedCurrentUse4 = lot.exit(car2);
		assertEquals(2,updatedCurrentUse4);  //lot capacity
		assertNotNull(lot.getExitTime(car2));	// exit time
	}

	
	// Testing accurate time stamps
	@Test
	public void testEntryExitTimes() {
		Address address = new Address("321 University Drive", "", "Denver", "CO", "80204");
		ParkingLot lot = new ParkingLot("PL001", address, 100, 5, 600, new HourlyRateStrategy());
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");

		//Testing time for entry is not null and before current time
		lot.entry(car);
		LocalDateTime entryTime = lot.getEntryTime(car);
		assertNotNull(entryTime);
		assertTrue(entryTime.isBefore(LocalDateTime.now()) || entryTime.isEqual(LocalDateTime.now()));

		/*assertTrue(entryTime.isBefore(LocalDateTime.now())||entryTime.isEqual(LocalDateTime.now()));*/
		
		// Testing time for exit is not null and before current time
		lot.exit(car);
		LocalDateTime exitTime = lot.getExitTime(car);
		assertNotNull(exitTime);
		assertTrue(exitTime.isBefore(LocalDateTime.now()) || exitTime.isEqual(LocalDateTime.now())
				&& exitTime.isAfter(entryTime));
	}
}