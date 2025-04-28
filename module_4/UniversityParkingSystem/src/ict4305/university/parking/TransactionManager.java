/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 3 Parking System Charge Calculator
 *      (Continuation of ICT 4305)
 * April 20, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;
import ict4305.university.parking.charges.factory.ParkingChargeStrategyFactory;
import ict4305.university.parking.charges.strategy.ParkingChargeStrategy;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.util.Objects;
import java.util.Collections;
import java.time.LocalDateTime;



public class TransactionManager {
	private List<ParkingTransaction> transactions = new ArrayList<>(); // list of all transactions
	private ParkingChargeStrategyFactory strategyFactory;
	
	public TransactionManager(ParkingChargeStrategyFactory strategyFactory) {
		if (strategyFactory == null) {
			throw new IllegalArgumentException("ParkingChargeSTrategyFactory can't be null.");
		}
		this.strategyFactory = strategyFactory;
	}
	
	public void postTransaction(ParkingLot lot, String strategyType, LocalDateTime entryTime, LocalDateTime exitTime, Car car) {
		Money charge = lot.calculateCharge(strategyType, entryTime, exitTime, car);
		ParkingTransaction transaction = new ParkingTransaction(entryTime, exitTime, car, charge); //where did ParkingTransaction and therefore charge come from
		transactions.add(transaction);
		}
	
	/*public TransactionManager() {
		this.transactions = new ArrayList<>();
	}
	*/
	
	// creating a transaction and adding to transaction list
	public void processParking(ParkingLot parkingLot, String strategyType, LocalDateTime entryTime, LocalDateTime exitTime, Car car) {
		if (parkingLot == null || strategyType == null || entryTime == null || exitTime == null || car == null) {
			throw new IllegalArgumentException("Parking details can't be null.");
		}
		ParkingChargeStrategy strategy = strategyFactory.getStrategy(strategyType);
		Money charge = strategy.calculateCharge(parkingLot.getBaseRate(), entryTime, exitTime, car);
		ParkingTransaction transaction = new ParkingTransaction(entryTime, exitTime, car, charge);
		transactions.add(transaction);
		/*ParkingTransaction transaction = new ParkingTransaction(timestamp, permit, lot);
		transactions.add(transaction);
		return transaction;*/
	}
	
	public Money calculateCharges() {
		Money totalCharge = new Money(0);
		for (Object obj : transactions) {
			ParkingTransaction transaction = (ParkingTransaction)obj;
			totalCharge = totalCharge.add(transaction.getCharge());
		}
	
	// calculating charge based on the transaction
	/*public Money calculateCharges(ParkingPermit permit) {
		return ((Object) transactions).stream()
				.map(ParkingTransaction::getCharge) // extracting a money object for each transaction
				.reduce(new Money(0), Money::add); //Summing all chrages starting from 0
		/*return transactions.stream()
				.filter(t -> t.getPermit().equals(permit))
				.map(ParkingTransaction::getCharge)
				.reduce(new Money(0), Money::add); // To sum charges for the permit*/
	}
	
	
	// calculating total charges for a customer
	public Money calculateChargeForCustomer(Customer customer) { // same as old only without the s in charges
		if(customer == null) {
			throw new IllegalArgumentException("Customer can't be null.");
		}
		
		return transactions.stream()
				.filter(transaction -> transaction.getCar().getOwner().equals(customer))
				.map(ParkingTransaction::getCharge)
				.reduce(new Money(0), Money::add);
		/*return transactions.stream()
				.filter(t -> t.getPermit().getOwner().equals(customer.getCustomerId()))
				.map(ParkingTransaction::getCharge)
				.reduce(new Money(0), Money:: add); // To sum charges for all permits owned by the customer*/
	}
	
	// getting transactions
	public List<ParkingTransaction> getTransactions() {
		return new ArrayList<>(transactions); // return a copy  // could be an error from parkingTransaction
	}
	
	// Equals method used to compare TransactionManagers
	// comparing lists of transactions
	// Assists in identifying duplicates
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true; // Same reference
	        if (!(o instanceof TransactionManager)) return false; // Ensure type match
	        TransactionManager that = (TransactionManager) o;
	        return Objects.equals(transactions, that.transactions) &&
	        		Objects.equals(strategyFactory, that.strategyFactory); // Compare transactions lists
	    }

     // Creates a specific HashCode that can be used for comparison
	 @Override
	 public int hashCode() {
		 return Objects.hash(transactions, strategyFactory); // Use transactions for hashCode
     }

}
