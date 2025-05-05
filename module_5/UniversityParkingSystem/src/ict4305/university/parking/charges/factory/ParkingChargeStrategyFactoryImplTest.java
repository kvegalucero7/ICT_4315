/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.charges.factory;

import static org.junit.jupiter.api.Assertions.*;
import ict4305.university.parking.charges.strategy.HourlyRateStrategy;
import ict4305.university.parking.charges.strategy.PerEntryStrategy;
import ict4305.university.parking.charges.strategy.ParkingChargeStrategy;
import org.junit.jupiter.api.Test;

public class ParkingChargeStrategyFactoryImplTest {
	
	// Testing to ensure the factory is using the Hourly Rate Strategy
	@Test
	public void testGetHourlyStrategy() {
		ParkingChargeStrategyFactory factory = new ParkingChargeStrategyFactoryImpl();
		ParkingChargeStrategy strategy = factory.getStrategy("HOURLY");
		assertTrue(strategy instanceof HourlyRateStrategy, "Factory should return HourlyRateStrategy.");
	}
	
	// Testing to ensure the factory is using the Per Entry Strategy
	@Test
	public void testGetPerEntryStrategy() {
		ParkingChargeStrategyFactory factory = new ParkingChargeStrategyFactoryImpl();
		ParkingChargeStrategy strategy = factory.getStrategy("PER_ENTRY");
		assertTrue(strategy instanceof PerEntryStrategy, "Factory should return PerEntryStrategy.");
	}
	
	// Testing to ensure the factory has an exception in place for an unknown strategy
	@Test
	public void testUnknownStrategy() {
		ParkingChargeStrategyFactory factory = new ParkingChargeStrategyFactoryImpl();
		assertThrows(IllegalArgumentException.class, ()-> factory.getStrategy("INVALID"),"Unknown strategy shouldthrow an exception");
	}

}
