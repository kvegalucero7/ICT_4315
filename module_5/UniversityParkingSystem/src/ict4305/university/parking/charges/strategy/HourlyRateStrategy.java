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

import java.time.Duration;
import java.time.LocalDateTime;
import ict4305.university.parking.Car;
import ict4305.university.parking.Money;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class HourlyRateStrategy implements ParkingChargeStrategy{
	@Override
	public Money calculateCharge(Money baseRate, LocalDateTime entryTime, LocalDateTime exitTime, Car car) {
		// Checking for null values
		if (entryTime == null || exitTime == null || car == null) {
			throw new IllegalArgumentException("Entry time, exit time, or car can't be null.");
		}
		
		// Calculating the hours Parked
		long hoursParked = calculateHoursParked(entryTime, exitTime);
		
		// Starting with hourly charges by multiplying base rate by hours parked
		Money finalRate = baseRate.multiply(hoursParked); 
		
		
		// Consideration 1: Vehicle type
		if (car.getCarType().name().equalsIgnoreCase("COMPACT")) { // Because CarType is Enum,convert to name for comparision
			finalRate = finalRate.multiply(0.8); //20% discount for compact cars
		}
				
		
		// Consideration 2: Day of the Week 
		DayOfWeek day = entryTime.getDayOfWeek();
		if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) { 
			finalRate = finalRate.multiply(0.8); // 20% discount on weekends
		}

				
		// Consideration 3: Time of Day
		// creating variables for easy use of entry and exit hour
		int entryHour = entryTime.getHour();
		int exitHour = exitTime.getHour();
		
		// Peak Hours = 9AM to 5PM but counting for more than 18hrs in case entry an exit time 
		// are outside of range but extend multiple days
		if (entryHour >= 9 && entryHour < 17  ||  exitHour >=9 && exitHour < 17 || hoursParked > 18) { 
			finalRate = finalRate.add(baseRate.multiply(0.25)); // Adding a 25% surcharge for peak hours
		}

			
		// Consideration 4: Special Days
		// Adding Graduation as a special day
		LocalDate graduationDay = LocalDate.of(2025, 05, 16); // Graduation set for Friday, May 16, 2025
		if (entryTime.toLocalDate().isEqual(graduationDay)) {
			finalRate = finalRate.add(baseRate.multiply(0.5)); // adding a 50% surcharge for graduation
		}
		
		// Adding tail gating event for home coming
		LocalDate tailGateDay = LocalDate.of(2025, 10, 11); // TailGate Day set for Saturday, October 11, 2025
		if (entryTime.toLocalDate().isEqual(tailGateDay)) {
			finalRate = finalRate.add(baseRate.multiply(0.20)); // adding a 20% surcharge for graduation
		}

		
		// Consideration 5: Overnight 
		// stays which are counted between 12 AM and 6 AM or hours parked is more than 18 hours
		if (entryHour < 6 || exitHour < 6 || hoursParked > 18) {
			finalRate = finalRate.add(new Money(1500)); // flat $15 fee (as cents) for overnight charges
		}
				
		return finalRate;
	}
	
	public long calculateHoursParked(LocalDateTime entryTime, LocalDateTime exitTime) {
		long hoursParked = Duration.between(entryTime, exitTime).toHours();
		if(Duration.between(entryTime, exitTime).toMinutes() % 60 > 0) { //checking for remainder to round up to next whole hour
			hoursParked += 1; // Rounds up for any partial hours
		}
		return hoursParked;
	}
	
	
}
