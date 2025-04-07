/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 1 Parking System (Continuation of ICT 4305)
 * April 6, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.util.Objects;
import java.time.Instant;

public class ParkingTransaction {
	private Instant timestamp; // time of transaction
	private ParkingPermit permit; // permit involved in transaction
	private ParkingLot lot; // parking lot involved in transaction 
	private Money charge; // charge during transaction
	
	public ParkingTransaction(Instant timestamp, ParkingPermit permit, ParkingLot lot) {
		if(timestamp==null ||permit==null || lot==null) {
			throw new IllegalArgumentException("Transaction fields can't be null");
		}
		this.timestamp = timestamp;
		this.permit = permit;
		this.lot = lot;
		this.charge = calculateCharge();
	}
	
	// Calculating charge factoring car type for 20% discount
	private Money calculateCharge() {
		long rate = lot.getCapacity() > 100 ? 500 : 1000; //example
		if(permit.getCar().getCarType() == CarType.COMPACT) {
			rate *= 0.8; // 20% discount for compact cars
		}
		return new Money(rate);
	}
	
	
	// Getters
	public Instant getTimestamp() {
		return timestamp;
	}
	
	public ParkingPermit getPermit() {
		return permit;
	}
	
	public ParkingLot getLot() {
		return lot;
	}
	
	public Money getCharge() {
		return charge;
	}
	
	// Equals method used to compare transactions
	// Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof ParkingTransaction)) return false;
		ParkingTransaction that = (ParkingTransaction) o;
		return Objects.equals(timestamp, that.timestamp) &&
			   Objects.equals(permit, that.permit) &&
			   Objects.equals(lot, that.lot);
	}
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() {
		return Objects.hash(timestamp, permit, lot);
	}
	
	// Return srting with details on parking transaction
	@Override
	public String toString() {
		return "ParkingTransaction--[timestamp=" + timestamp + ", permit=" + permit + ", lot=" + lot + ", charge=" + charge + "]";
	}
	
}
