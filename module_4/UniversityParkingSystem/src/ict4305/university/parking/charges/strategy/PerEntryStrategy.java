/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 20, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.charges.strategy;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.Duration;
import ict4305.university.parking.Car;
import ict4305.university.parking.Money;

public class PerEntryStrategy implements ParkingChargeStrategy {
	@Override
	public Money calculateCharge(Money baseRate, LocalDateTime entryTime, LocalDateTime exitTime, Car car) {
		Money finalRate = baseRate;
		long hoursParked = calculateHoursParked(entryTime, exitTime);
		
		// Consideration 1: Vehicle type
	    if (car.getCarType().name().equalsIgnoreCase("COMPACT")) { // Because CarType is Enum,convert to name for comparision
			finalRate = finalRate.multiply(0.8); //20% discount for compact cars
		}
		
		// Consideration 2: Day of the Week 
		DayOfWeek day = entryTime.getDayOfWeek();
		if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
			finalRate = finalRate.multiply(0.8); // 20% discount on weekends
		}
		
		// Consideration 3: Special days
		// Graduation Day Friday May 16, 2025
		LocalDate graduationDay = LocalDate.of(2025, 05, 16); // Graduation set for May 16, 2025
		if (entryTime.toLocalDate().isEqual(graduationDay)) {
			finalRate = finalRate.add(baseRate.multiply(0.5)); // adding a 50% surcharge for graduation
		}
		// Home Coming Saturday October 11, 2025 
		LocalDate tailGateDay = LocalDate.of(2025, 10, 11); // TailGate Day set for October 11, 2025
		if (entryTime.toLocalDate().isEqual(tailGateDay)) {
			finalRate = finalRate.add(baseRate.multiply(0.2)); // adding a 20% surcharge for graduation
		}
		
		
		// Consideration 4: Overnight		
		// Considering overnight stays which are counted between 12 AM and 6 AM or hours parked is more than 18 hours
		int exitHour = exitTime.getHour();
		if (exitHour < 6 && hoursParked > 12 || hoursParked > 18) {
			finalRate = finalRate.add(new Money(1500)); // flat $15 fee for overnight charges
		}
		
		return finalRate;
	}
	
	// Calculating Hours between entry and exit times
	public long calculateHoursParked(LocalDateTime entryTime, LocalDateTime exitTime) {
		long hoursParked = Duration.between(entryTime, exitTime).toHours();
		if(Duration.between(entryTime, exitTime).toMinutes() % 60 > 0) { //checking for remainder to round up to next whole hour
			hoursParked += 1; // Rounds up for any partial hours
		}
		return hoursParked;
	}
}
