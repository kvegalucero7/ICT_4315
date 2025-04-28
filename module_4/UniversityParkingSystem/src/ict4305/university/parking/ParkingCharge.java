/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 27, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.time.Instant;

public class ParkingCharge {
	private String permitId; // permitId incurring charge
	private String lotId; // parkingLot assigning charge
	private Instant incurred; // instant charge was incurred
	private Money amount; // amount incurred
	
	public ParkingCharge(String permitId, String lotId, Instant incurred, Money amount) {
		this.permitId = permitId;
		this.lotId = lotId;
		this.incurred = incurred;
		this.amount = amount;
	}
	
	// Getters
	// Retrieving permitId as String
	public String getPermitId() {
		return permitId;
	}
	
	// Retrieving lotId as String
	public String  getLotId() {
		return lotId;
	}
	
	// Retrieving incurred (when) as Instant
	public Instant getIncurred() {
		return incurred;
	}
	
	// Retrieving amount incurred as Money
	public Money getAmount() {
		return amount;
	}
	
	// to String method that returns details on parking charge
	@Override
	public String toString() {
		return "ParkingCharge--[permitId=" + permitId + ", lotId=" + lotId + ", incurred=" + incurred + ", amount=" + amount + "]";
	}
}

