/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 27, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CarTest {

	@Test
	public void testCarAttributes() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		
		assertEquals("123ABC", car.getPermit()); // Testing permit
		assertEquals(LocalDate.now().plusYears(1), car.getPermitExp()); // Testing permit expiration
		assertEquals("123ABC", car.getLicense()); // Testing license
		assertEquals(CarType.COMPACT, car.getCarType()); // Testing CarType
		assertEquals("CI00001", car.getOwner()); // Testing Owner
	}

	
	
	// Testing the toString method
	@Test
	public void testToString() {
		Car car = new Car("123ABC", LocalDate.now().plusYears(1), "123ABC", CarType.COMPACT, "CI00001");
		
		String expected = "Car--[permit=123ABC, permitExp=" + LocalDate.now().plusYears(1) + ", license=123ABC, type=" 
	            + "COMPACT, owner=CI00001]";
		assertEquals(expected, car.toString());
	}

	
	
	// Testing with null fields 
	// null permit
	@Test
	public void testCarConstructorNullPermit() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car(null, LocalDate.now().plusYears(1),"XYZ789", CarType.SUV, "CI0003");
		});
		assertEquals("Permit can't be null or empty.", thrown.getMessage());
	}
	
	// null permitExp
	@Test
	public void testCarConstructorNullPermitExp() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car("123ABC", null,"XYZ789", CarType.SUV, "CI0003");
		});
		assertEquals("Permit expiration can't be null and must be in the future.", thrown.getMessage());
	}
		
    // null license
	@Test
	public void testCarConstructorNullLicense() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car("123ABC", LocalDate.now().plusYears(1),null, CarType.SUV, "CI0003");
		});
		assertEquals("License can't be null or empty.", thrown.getMessage());
	}
	
	// null type
	@Test
	public void testCarConstructorNullType() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car("123ABC", LocalDate.now().plusYears(1), "XYZ789", null, "CI0003");
		});
		assertEquals("Car Type can't be null.", thrown.getMessage());
	}
	
	// null owner
	@Test
	public void testCarConstructorNullOwner() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car("123ABC", LocalDate.now().plusYears(1), "XYZ789", CarType.SUV, null);
		});
		assertEquals("Owner can't be null or empty.", thrown.getMessage());
	}

	
	
	// Testing past permit past expiration date
	@Test
	public void testCarConstructorPastPermitExp() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car("XYZ789", LocalDate.now().minusDays(1),"XYZ789", CarType.SUV, "CI0003");
		});
		assertEquals("Permit expiration can't be null and must be in the future.", thrown.getMessage());
	}
	
	
	
	// Testing with empty fields 
	// empty permit
	@Test
	public void testCarConstructorEmptyPermit() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car("", LocalDate.now().plusYears(1),"XYZ789", CarType.SUV, "CI0003");
		});
		assertEquals("Permit can't be null or empty.", thrown.getMessage());
	}
		
			
	// empty license
		@Test
		public void testCarConstructorEmptyLicense() {
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
				new Car("123ABC", LocalDate.now().plusYears(1), "", CarType.SUV, "CI0003");
			});
			assertEquals("License can't be null or empty.", thrown.getMessage());
		}
		
		
	// null owner
	@Test
	public void testCarConstructorEmptyOwner() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			new Car("123ABC", LocalDate.now().plusYears(1), "XYZ789", CarType.SUV, "");
		});
		assertEquals("Owner can't be null or empty.", thrown.getMessage());
		}
	
	
	
	// Testing equals and hashCode
	@Test
	public void testEqualsAndHashCode() {
		Car car1 = new Car("123ABC", LocalDate.now().plusYears(1), "XYZ789", CarType.SUV, "CI0003");
	    Car car2 = new Car("123ABC", LocalDate.now().plusYears(1), "XYZ789", CarType.SUV, "CI0003");
	    assertEquals(true, car1.equals(car2));
	    assertEquals(car1.hashCode(), car2.hashCode());
	}

}
