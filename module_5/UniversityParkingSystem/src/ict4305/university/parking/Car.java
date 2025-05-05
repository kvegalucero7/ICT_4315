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
import java.time.LocalDate;
import java.util.Objects;

/*
 * This class works with cars registered to a customer
 * It stores license plate, type of car and parking permit
 */

public class Car {
	private String permit; // Unique parking permit ID
	private LocalDate permitExp; // Permit expiration date
	private String license; // Car license plate
	private CarType type; // Type of car: COMPACT or SUV
	private String owner; // CustomerId for owner

	// Need to include the random gen. permit here as well
	public Car(String permit, LocalDate permitExp, String license, CarType type, String owner) {
		//Preventing null or empty permit
		if (permit == null || permit.isEmpty()) {
			throw new IllegalArgumentException("Permit can't be null or empty.");
		}
		//Preventing null or empty permit expiration date
		if (permitExp == null || permitExp.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Permit expiration can't be null and must be in the future.");
		}
		//Preventing null or empty license plate
		if (license == null || license.isEmpty()) {
			throw new IllegalArgumentException("License can't be null or empty.");
		}
		//Preventing null car type
		if (type == null) {
			throw new IllegalArgumentException("Car Type can't be null.");
		}
		//Preventing null or empty owner
		if (owner == null || owner.isEmpty()) {
			throw new IllegalArgumentException("Owner can't be null or empty.");
		}
		this.permit = permit;
		this.permitExp = permitExp;
		this.license = license;
		this.type = type;
		this.owner = owner;
	}
	
	public Car(String license, CarType type, String owner) {
		//Preventing null or empty license plate
		if (license == null || license.isEmpty()) {
			throw new IllegalArgumentException("License can't be null or empty.");
		}
		//Preventing null car type
		if (type == null) {
			throw new IllegalArgumentException("Car Type can't be null.");
		}
		//Preventing null or empty owner
		if (owner == null || owner.isEmpty()) {
			throw new IllegalArgumentException("Owner can't be null or empty.");
		}
		this.license = license;
		this.type = type;
		this.owner = owner;
	}
	// Getters
	// Returns car permit
	public String getPermit() {
		return permit;
	}
	 
	// Returns car permit expiration
	public LocalDate getPermitExp() {
		return permitExp;
	}
	
	// Returns car license plate
	public String getLicense() {
		return license;
	}
	
	// Returns car type
	public CarType getCarType() {
		return type;
	}
	
	// Returns car owner
	public String getOwner() {
		return owner;
	}
	
	// Returns a string with car details in a string
	@Override
	public String toString() {
		return "Car--["+
	             "permit=" + permit +
	             ", permitExp=" + permitExp +
	             ", license=" + license +
	             ", type=" + type + 
	             ", owner=" + owner + 
	            "]";
	}
	
	// Equals method used to compare cars
    // Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Car car = (Car) o;
		return permit.equals(car.permit) &&
				permitExp.equals(car.permitExp) &&
				license.equals(car.license) &&
				type == car.type &&
				owner.equals(car.owner);
	}
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() {
		return Objects.hash(permit, permitExp, license, type, owner);
	}
}
