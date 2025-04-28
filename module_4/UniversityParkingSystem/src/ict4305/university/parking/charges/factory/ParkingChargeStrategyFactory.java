package ict4305.university.parking.charges.factory;

import ict4305.university.parking.charges.strategy.ParkingChargeStrategy;

public interface ParkingChargeStrategyFactory {
	ParkingChargeStrategy getStrategy(String strategyType);
}
