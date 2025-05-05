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
//import ict4305.university.parking.charges.factory.ParkingChargeStrategyFactory;
import ict4305.university.parking.charges.strategy.ParkingChargeStrategy;
import ict4305.university.parking.events.ParkingEvent;

import java.util.ArrayList;
import java.util.List;
//import java.time.Instant;
import java.util.Objects;
//import java.util.Collections;
import java.time.LocalDateTime;
//import java.util.stream.Collectors;



public class TransactionManager {
	private List<ParkingTransaction> transactions = new ArrayList<>(); // list of all transactions
	
	public void park(ParkingEvent event) {
		if (event == null || event.getPermit() == null || event.getParkingLot() == null) {
			throw new IllegalArgumentException("Invalid parking event.");
		}
		if(!event.isEntry()) {
			postTransaction(event.getParkingLot(), event.getTimestamp(), event.getTimestamp(), event.getPermit().getCar());
		}
	}
	
	
	
	// getting transactions
	public List<ParkingTransaction> getTransactions() {
		return new ArrayList<>(transactions); // return a copy  // could be an error from parkingTransaction
	}
	
	public TransactionManager() {
	}
	
	public void postTransaction(ParkingLot parkingLot, LocalDateTime entryTime, LocalDateTime exitTime, Car car) {
		if (parkingLot == null || entryTime == null || exitTime == null || car == null) {
			throw new IllegalArgumentException("Transaction details can't be null.");
		}
		
		if (exitTime.isBefore(entryTime)) {
			throw new IllegalArgumentException("Exit time can't be before entry time.");
		}
		
		ParkingChargeStrategy strategy = parkingLot.getChargeStrategy();
		Money charge = strategy.calculateCharge(parkingLot.getBaseRate(), entryTime, exitTime, car);
		ParkingTransaction transaction = new ParkingTransaction(entryTime, exitTime, car, charge);
		transactions.add(transaction);
		System.out.println("Manual transaction posted: " + transaction);
	}
	
	public Money calculateCharges() {
		return transactions.stream()
				.map(ParkingTransaction::getCharge) // extracting a money object for each transaction
				.reduce(new Money(0), Money::add); //Summing all chrages starting from 0
	}
	
	
	// calculating total charges for a customer
	public Money calculateChargeForCustomer(Customer customer) { // same as old only without the s in charges
		if(customer == null) {
			throw new IllegalArgumentException("Customer can't be null.");
		}
		
		//System.out.println("testing customer transaction for CID: " + customer.getCustomerId());
		return transactions.stream()
				.filter(transaction -> {
					//System.out.println("Transaction owner: " + transaction.getCar().getOwner());
					return transaction.getCar().getOwner().equals(customer.getCustomerId());
				})
				.map(ParkingTransaction::getCharge)
				.reduce(new Money(0), Money::add);
	}
	
	
	// Equals method used to compare TransactionManagers
	// comparing lists of transactions
	// Assists in identifying duplicates
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true; // Same reference
	        if (!(o instanceof TransactionManager)) return false; // Ensure type match
	        TransactionManager that = (TransactionManager) o;
	        return Objects.equals(transactions, that.transactions);// Compare transactions lists
	    }

     // Creates a specific HashCode that can be used for comparison
	 @Override
	 public int hashCode() {
		 return Objects.hash(transactions); // Use transactions for hashCode
     }

}
