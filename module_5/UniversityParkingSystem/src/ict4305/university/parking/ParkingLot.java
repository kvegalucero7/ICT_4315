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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import ict4305.university.parking.charges.strategy.ParkingChargeStrategy;
import ict4305.university.parking.charges.factory.ParkingChargeStrategyFactory;
import ict4305.university.parking.events.ParkingEvent;
import ict4305.university.parking.observer.ParkingObserver;



public class ParkingLot {
	private String lotId; // parking lot ID
	private Address address; // Parking lot Address
	private int capacity; // Parking lot total capacity
	private int currentUse; // Parking lot used capacity
	private Map<String, LocalDateTime> entryTimes; // time entering parking lot
	private Map<String, LocalDateTime> exitTimes; // Time exiting parking lot
	private ParkingChargeStrategy chargeStrategy; // Strategy for calculating parking charges
	private Money baseRate; // Flat base rate for parking
	private ParkingChargeStrategyFactory strategyFactory;
	private List<ParkingObserver> observers = new ArrayList<>();

	public ParkingLot(String lotId, Money baseRate, ParkingChargeStrategyFactory strategyFactory) {
		this.lotId = lotId;
		this.baseRate = baseRate;
		this.strategyFactory = strategyFactory;
	}
	
	public ParkingLot(String lotId, Address address, int capacity, int currentUse, long baseRateCents, 
			ParkingChargeStrategy chargeStrategy) {
		if (lotId == null || lotId.isEmpty()) {
			throw new IllegalArgumentException("Lot ID has not been set yet.");
		}
		if (address == null) {
			throw new IllegalArgumentException("Address has not been set yet.");
		}
		if (chargeStrategy == null) {
			throw new IllegalArgumentException("Charge Strategy has not been set yet.");
		}
		this.lotId = lotId;
		this.address = address;
		this.capacity = capacity;
		this.currentUse = currentUse;
		this.entryTimes = new HashMap<>();
		this.exitTimes = new HashMap<>();
		this.baseRate = new Money(baseRateCents);
		this.chargeStrategy = chargeStrategy;
	}
	
	
	// Getting and setting base Rate
	public Money getBaseRate() {
		return baseRate;
	}
	
	public void setBaseRate(Money baseRate) {
		this.baseRate = baseRate;
	}
	
	// Updating parking lot vacancy upon entry and noting car license and time upon entry
	public int entry(Car car) {
		if (currentUse >= capacity) {
			throw new IllegalArgumentException("Parking lot is currently full. ");
		}
		increaseUsage(); // updating vacancy
		entryTimes.put(car.getLicense(), LocalDateTime.now()); // recoding license and time of entry
		return currentUse; // returning spots used
	}
	
	// method that increases parking lot capacity
	public void increaseUsage() {
		if (currentUse >= capacity) {
			throw new IllegalArgumentException("Parking lot is currently full. ");
		}else {
		    currentUse++;
		}
	}
	
	// method that decreases parking lot capacity
	public void decreaseUsage() {
		if (currentUse > 0) {
		    currentUse--;
		} else {
			System.out.println("Warning: Attempted to decrease capacity below zero.");
		}
	}
	
	// Updating parking lot vacancy upon exit and noting car license and time upon exit
	public int exit(Car car) {
		if(currentUse>0) {
			decreaseUsage(); // updating vacancy
			exitTimes.put(car.getLicense(), LocalDateTime.now()); // recording license and time of exit
		}
		return currentUse; // returning spots used
	}
	
	
	// getting the type of charge strategy used
	public ParkingChargeStrategy getChargeStrategy() {
		return chargeStrategy;
	}
	
	// setting the charge strategy to be used
	public void setChargeStrategy(ParkingChargeStrategy chargeStrategy) {
		this.chargeStrategy = chargeStrategy;
	}
	
	// Parking lot charge calculation that calls a parking charge strategy
	public Money calculateCharge(String strategyType, LocalDateTime entryTime, LocalDateTime exitTime, Car car) {
		ParkingChargeStrategy strategy = strategyFactory.getStrategy(strategyType);
		return strategy.calculateCharge(baseRate,  entryTime, exitTime, car);
	}
	
	// calculating the charge based on the charging strategy used
	public Money calculateParkingCharge(LocalDateTime entryTime, LocalDateTime exitTime, Car car) {
		if (entryTimes == null) {
			throw new IllegalArgumentException("Entry time can't be null.");
			}
		
		if (exitTimes == null) {
			throw new IllegalArgumentException("Exit time can't be null.");
			}
		
		if (car.getCarType() == null || car.getCarType().name().isEmpty()) {
			throw new IllegalArgumentException("Car Type can't be null or empty.");
		}
		
		if (exitTime.isBefore(entryTime)) {
			throw new IllegalArgumentException("Exit time can't be before entry time.");
		}
		return chargeStrategy.calculateCharge(baseRate, entryTime, exitTime, car);
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
		return "ParkingLot--[lotId=" + lotId + ", address=" + address + ", capacity=" + capacity 
				+ ", usage=" + currentUse +"]";
	}
	
	// method to add and observer
	public void addObserver(ParkingObserver observer) {
		observers.add(observer);
	}
	
	// method to remove an observer
	public void removeObserver(ParkingObserver observer) {
		observers.remove(observer);
	}
	
	// method to notify observers
	public void notifyObservers(ParkingEvent event) {
		for (ParkingObserver observer : observers) {
			observer.update(event);
		}
	}
	
	// entering the parking lot with notification
	public void enter(ParkingPermit permit) {
		ParkingEvent event = new ParkingEvent(LocalDateTime.now(), permit, this, true);
		notifyObservers(event);
	}
	
	// exiting the parking lot with notification
	public void exit(ParkingPermit permit) {
		ParkingEvent event = new ParkingEvent(LocalDateTime.now(), permit, this, false);
		notifyObservers(event);
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
