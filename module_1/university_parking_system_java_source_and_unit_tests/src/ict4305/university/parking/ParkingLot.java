/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 1 Parking System (Continuation of ICT 4305)
 * April 6, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {
	private String lotId; // parking lot ID
	private Address address; // PAlring lot Address
	private int capacity; // Parking lot total capacity
	private int currentUse; // Parking lot used capacity
	private Map<String, LocalDateTime> entryTimes; // time entering parking lot
	private Map<String, LocalDateTime> exitTimes; // Time exiting parking lot

	
	public ParkingLot(String lotId, Address address, int capacity, int currentUse) {
		this.lotId = lotId;
		this.address = address;
		this.capacity = capacity;
		this.currentUse = currentUse;
		this.entryTimes = new HashMap<>();
		this.exitTimes = new HashMap<>();
	}
	
	// Updating parking lot vacancy upon entry and noting car license and time upon entry
	public int entry(Car car) {
		if (currentUse >= capacity) {
			throw new IllegalArgumentException("Parking lot is currently full. ");
		}
		currentUse++; // updating vacancy
		entryTimes.put(car.getLicense(), LocalDateTime.now()); // recoding license and time of entry
		return currentUse; // returning spots used
	}
	
	// Updating parking lot vacancy upon exit and noting car license and time upon exit
	public int exit(Car car) {
		if(currentUse>0) {
			currentUse--; // updating vacancy
			exitTimes.put(car.getLicense(), LocalDateTime.now()); // recording license and time of exit
		}
		return currentUse; // returning spots used
	}
	
	// Equals method used to compare parkingLots
	// Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		ParkingLot that = (ParkingLot) o;
		return capacity == that.capacity &&
				lotId.equals(that.lotId) &&
				address.equals(that.address);
	}
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() {
		return Objects.hash(lotId, address, capacity);
	}
	
	// returns a string with parking lot details
	@Override
	public String toString() {
		return "ParkingLot--[lotId=" + lotId + ", address=" + address + ", capacity=" + capacity + ", usage=" + currentUse +"]";
	}
	
	
	// Getters
	// getting lot Id
	public String getLotId() {
		return lotId;
	}
	
	// Getting lot address
	public Address getAddress() {
		return address;
	}
	
	// Getting lot capacity
	public int getCapacity() {
		return capacity;
	}
	
	// Getting lots used spaces
	public int getCurrentUse() {
		return currentUse;
	}
	
	// Getting time car entered
	public LocalDateTime getEntryTime(Car car) {
		return entryTimes.get(car.getLicense());
	}
	
	// Getting time car left
	public LocalDateTime getExitTime(Car car) {
		return exitTimes.get(car.getLicense());
	}
	
}
/*
 * References
 * 
 * W3Schools. n.d. "Java HashMap." W3Schools. Accessed February 1, 2025.
 *    https://www.w3schools.com/java/java_hashmap.asp.
 */
