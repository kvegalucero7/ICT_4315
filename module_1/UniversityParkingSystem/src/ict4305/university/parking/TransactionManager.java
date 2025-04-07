/*
 * Kassandra Vega Lucero
 * 
 * ICT 4315: Week 1 Parking System (Continuation of ICT 4305)
 * April 6, 2025
 * Instructor: Nathan Braun
 * 
 */
package ict4305.university.parking;

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.util.Objects;

public class TransactionManager {
	private List<ParkingTransaction> transactions; // list of all transactions
	
	public TransactionManager() {
		this.transactions = new ArrayList<>();
	}
	
	// creating a transactiona nd adding to transaction list
	public ParkingTransaction processParking(Instant timestamp, ParkingPermit permit, ParkingLot lot) {
		ParkingTransaction transaction = new ParkingTransaction(timestamp, permit, lot);
		transactions.add(transaction);
		return transaction;
	}
	
	// calculating charge based on the transaction
	public Money calculateCharges(ParkingPermit permit) {
		return transactions.stream()
				.filter(t -> t.getPermit().equals(permit))
				.map(ParkingTransaction::getCharge)
				.reduce(new Money(0), Money::add); // To sum charges for the permit
	}
	
	
	// calculating total charges for a customer
	public Money calculateChargesForCustomer(Customer customer) {
		return transactions.stream()
				.filter(t -> t.getPermit().getOwner().equals(customer.getCustomerId()))
				.map(ParkingTransaction::getCharge)
				.reduce(new Money(0), Money:: add); // To sum charges for all permits owned by the customer
	}
	
	// getting transactions
	public List<ParkingTransaction> getTransactions(){
		return new ArrayList<>(transactions); // return a copy
	}
	
	// Equals method used to compare TransactionManagers
	// comparing lists of transactions
	// Assists in identifying duplicates
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true; // Same reference
	        if (!(o instanceof TransactionManager)) return false; // Ensure type match
	        TransactionManager that = (TransactionManager) o;
	        return Objects.equals(transactions, that.transactions); // Compare transactions lists
	    }

     // Creates a specific HashCode that can be used for comparison
	 @Override
	 public int hashCode() {
		 return Objects.hash(transactions); // Use transactions for hashCode
     }

}
