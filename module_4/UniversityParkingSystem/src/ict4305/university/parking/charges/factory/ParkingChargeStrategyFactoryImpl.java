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