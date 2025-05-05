/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * May 4, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking.events;

import ict4305.university.parking.ParkingPermit;
import ict4305.university.parking.ParkingLot;
import java.time.LocalDateTime;

public class ParkingEvent {
	private final LocalDateTime timestamp;
	private final ParkingPermit permit;
	private final ParkingLot lot;
	private final boolean isEntry;
	
	public ParkingEvent(LocalDateTime timestamp, ParkingPermit permit, ParkingLot lot, boolean isEntry) {
		this.timestamp = timestamp;
		this.permit = permit;
		this.lot = lot;
		this.isEntry = isEntry;
	}
	
	// getters
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public ParkingPermit getPermit() {
		return permit;
	}
	
	public ParkingLot getParkingLot() {
		return lot;
	}
	
	public boolean isEntry() {
		return isEntry;
	}
	
	@Override
	public String toString() {
		return (isEntry ? "Entry" : "Exit"); // To indicate whether someone is entering or exiting
	}
}
