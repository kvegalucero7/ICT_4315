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

import ict4305.university.parking.charges.strategy.ParkingChargeStrategy;
import ict4305.university.parking.charges.strategy.HourlyRateStrategy;
import ict4305.university.parking.charges.strategy.PerEntryStrategy;

public class ParkingChargeStrategyFactoryImpl implements ParkingChargeStrategyFactory{
	@Override
	public ParkingChargeStrategy getStrategy(String strategyType) {
		switch (strategyType.toUpperCase()) {
		case "HOURLY":
			return new HourlyRateStrategy();
		case "PER_ENTRY":
			return new PerEntryStrategy();
		default:
			throw new IllegalArgumentException("Strategy type unknown: " + strategyType);
		}
	}
}