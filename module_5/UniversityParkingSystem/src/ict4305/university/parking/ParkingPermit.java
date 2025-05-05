/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.util.Objects;

public class ParkingPermit {
	private String permitId; // permitId 
	private Car car; // car with the permitId
	
	public ParkingPermit(Car car) {
		if(car==null) {
			throw new IllegalArgumentException("Car can't be null.");
		}
		this.car = car;
		this.permitId = car.getPermit();
	}
	
	// Getters
	public String getPermitId() {
		return permitId;
	}
	
	public Car getCar() {
		return car;
	}
	
	public String getOwner() {
		return car.getOwner();
	}
	
	// Equals method used to compare ParkingPermits
	// Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof ParkingPermit)) return false;
		ParkingPermit permit = (ParkingPermit) o;
		return Objects.equals(permitId, permit.permitId);
	}
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() {
		return Objects.hash(permitId);
	}
}
