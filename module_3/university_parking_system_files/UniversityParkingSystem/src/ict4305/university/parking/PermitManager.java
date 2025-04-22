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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Objects;


public class PermitManager {
	private Set<String> permitIds; // Tracks unique permit IDs
	private Set<String> customerIds; // Tracks unique customer IDs
	private List<Customer> customers; // List of registered customers
	private Set<ParkingPermit> permits; // List of registered permits
	
	public PermitManager() {
		this.permitIds = new HashSet<>();
		this.customerIds = new HashSet<>();
		this.customers = new ArrayList<>();
		this.permits = new HashSet<>();
	}
	
	// Registering new customer
	public void registerCustomer(Customer customer) {
		if (customerIds.contains(customer.getCustomerId())) {
			throw new IllegalArgumentException("Customer is already registered");
		}
		customers.add(customer); // adding to list of customers
		customerIds.add(customer.getCustomerId()); // adding customerID to set of customers
	}
	
	// Registering car  and returning permit
	public ParkingPermit registerCar(Car car) {
		if (permitIds.contains(car.getPermit())) { 
			throw new IllegalArgumentException("Permit already issued for this car."); // not allowing duplicate car registrations
		}
		ParkingPermit permit = new ParkingPermit(car);
		permitIds.add(permit.getPermitId()); // adding permitId to set of permitIds
		permits.add(permit); // adding permit to set of permits
		return permit;
	}
	
	// Getting all registered customers
	public List<Customer> getRegisteredCustomers(){
		return new ArrayList<>(customers); // Copy of permit list
	}
	
	// Getting all registered permits
	public Set<ParkingPermit> getRegisteredPermits(){
		return new HashSet<>(permits); // Copy of permit list
	}
	
	// getting all permitIds for a single customer
	public List<String> getPermitIdsForCustomer(Customer customer){
		if(customer == null) {
			throw new IllegalArgumentException("Customer can't be null");
		}
		
		if(!customerIds.contains(customer.getCustomerId())) {
			throw new IllegalArgumentException("Customer is not registered");
		}
		List<String> customerPermitIds = new ArrayList<>();
		for (ParkingPermit permit : permits) {
			if(permit.getCar().getOwner().trim().equals(customer.getCustomerId())) {
				customerPermitIds.add(permit.getPermitId());
			}
		}
		return customerPermitIds;
	}
	
	// remove a customer from the set of customerIds
	public void removeCustomer(String customerId) {
		customers.removeIf(customer -> customer.getCustomerId().equals(customerId)); // checking/removing if in list using customerId
		permits.removeIf(permit -> permit.getCar().getOwner().equals(customerId)); // checking/removing if is in set using customerId
		customerIds.remove(customerId); //removing customerId from set
	}
	
	// checking is customer is registered using their customerId
	public boolean isCustomerRegistered(String customerId) {
		return customerIds.contains(customerId);
	}
	
	// Checking total number of customers and permits
	public Map<String, Integer> getStatistics(){
		Map<String, Integer> stats = new HashMap<>();
		stats.put("Total Customers", customers.size()); // size gives total customers
		stats.put("Total Permits", permits.size()); // size give total permits
		return stats;
	}
	
	// Equals method used to compare multiple permit managers 
	// It includes various components for comparison
	// Assists in identifying duplicates
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    PermitManager that = (PermitManager) obj;
	    return Objects.equals(permitIds, that.permitIds) &&
	           Objects.equals(customerIds, that.customerIds) &&
	           Objects.equals(customers, that.customers) &&
	           Objects.equals(permits, that.permits);
	}

	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() {
	    return Objects.hash(permitIds, customerIds, customers, permits);
	}

}
