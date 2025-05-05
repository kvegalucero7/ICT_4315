/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 27, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.charges.strategy;

import java.time.LocalDateTime;
import ict4305.university.parking.Car;
import ict4305.university.parking.Money;

public interface ParkingChargeStrategy {
	Money calculateCharge(Money baseRate, LocalDateTime entryTime, LocalDateTime exitTime, Car car);
}
