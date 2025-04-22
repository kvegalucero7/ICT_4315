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
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

public class ParkingOffice {
	private String name; // Parking office name
	private Address address; // parking office address
	private List<Customer> customers; // Listing customer names
	private List<Car> cars; // listing cars
	private List<ParkingLot> lots; // listing parking lots
	private List<ParkingCharge> charges; // listing charges
	private Set<String> customerIds; // Set is used to keep track of used customerIds and avoid duplicates
	
	public ParkingOffice(String name, Address address) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Office name can't be null or empty."); // preventing invalid name
		}
		if (address == null) {
			throw new IllegalArgumentException("Office address can't be null."); // preventing invalid address
		}
		this.name = name;
		this.address = address;
		this.customers = new ArrayList<>();
		this.cars = new ArrayList<>();
		this.lots = new ArrayList<>();
		this.charges = new ArrayList<>();
		this.customerIds = new HashSet<>();
		}
	
	//Register a new customer and generate ID
	public Customer registerCustomer(String name, Address address, String phoneNumber) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Customer name can't be null or empty.");
		}
		if(address == null) {
			throw new IllegalArgumentException("Customer address can't be null.");
		}
		if(phoneNumber == null || phoneNumber.isEmpty()) {
			throw new IllegalArgumentException("Customer phone number can't be null or empty.");
		}
		String customerId = generateCustomerId();
		Customer customer = new Customer(customerId, name, address, phoneNumber);
		customers.add(customer); // adding customer to list of customers
		return customer;
	}
	
	//Register a new customer with chosen ID
		public Customer registerCustomer(String customerId, String name, Address address, String phoneNumber) {
			if(customerId == null || customerId.isEmpty()) {
				throw new IllegalArgumentException("CustomerId can't be null or empty.");
			}
			if(name == null || name.isEmpty()) {
				throw new IllegalArgumentException("Customer name can't be null or empty.");
			}
			if(address == null) {
				throw new IllegalArgumentException("Customer address can't be null.");
			}
			if(phoneNumber == null || phoneNumber.isEmpty()) {
				throw new IllegalArgumentException("Customer phone number can't be null or empty.");
			}
			Customer customer = new Customer(customerId, name, address, phoneNumber);
			customers.add(customer); // adding customer to list of customers
			return customer;
		}
	
	//Register a car for an existing customer
	public Car registerCar(Customer customer, String license, CarType type) {
		if(customer == null || license == null || license.isEmpty() || type == null) {
			throw new IllegalArgumentException("Customer, license and type can't be null or empty.");
		}
		Car car = customer.registerCar(license, type);
		cars.add(car); // adding car to list of cars
		return car;
	}
	
	//Get customer by ID
	public Optional<Customer> getCustomerById(String customerId){
		if(customerId == null || customerId.isEmpty()) {
			throw new IllegalArgumentException("Customer ID can't be null or empty.");
		}
		// looking for customer in list of customer  using their customerId and returning the first one (shortens time)
		return customers.stream().filter(c -> c.getCustomerId().equalsIgnoreCase(customerId)).findFirst(); 
		
	}
	
	//Get customer by name
	public List<Customer> getCustomersByName(String name){
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Customer name can't be null or empty.");
		}
		// looking for customer in list of customer using their name and returning the first one (shortens time)
		return customers.stream().filter(c -> c.getName().contentEquals(name)).collect(Collectors.toList());
	}
	
	//Generate a customer ID without having duplicate customer IDs
	private String generateCustomerId() {
		String customerId;
		// Do While loop generates a new random Id value while the customerId already exists
		do {                                            
			customerId = String.format("%06d", (int)(Math.random()*1_000_000));
		}while(customerIds.contains(customerId));
		customerIds.add(customerId); // adds customerId to list of customerIds
		return customerId;
	}
	
	//Get collection of customerIds
	public Set<String> getCustomerIds(){
		return customers.stream() // to go through the list
				.map(Customer::getCustomerId) // to extract values from list
				.collect(Collectors.toSet()); // Set of customer Ids is returned
	}
	
	//Get collection of all permitIds
	public Set<String> getPermitIds(){
		return cars.stream() // to go through the list
				.map(Car::getPermit) // to extract contents from list
				.collect(Collectors.toSet()); //// Set of permit Ids is returned for all cars
	}
	
	//Get all permitIds for a specific customer
	public Set<String> getPermitIds(Customer customer){
		if(customer == null) {
			throw new IllegalArgumentException("Customer can't be null.");
		}
		return customer.getCars().stream() // to go through the list of cars
				.map(Car::getPermit) // to extract from the permits from list
				.collect(Collectors.toSet()); //returns set of all permit Ids linked to a single customer
	}
	
	// Getters
	// gets  all customers
	public List<Customer> getCustomers() {
		return customers;
	}
	
	// Gets all cars
	public List<Car> getCars() {
		return cars;
	}
	
	// Gets all parking lots
	public List<ParkingLot> getLots() {
		return lots;
	}
	
	// gets all charges
	public List<ParkingCharge> getCharges() {
		return charges;
	}
	

	//Add a parking charge
	public Money addCharge(ParkingCharge charge) {
		if(charge == null) {
			throw new IllegalArgumentException("Charge can't be null.");
		}
		charges.add(charge);
		return charge.getAmount();
	}
	
	
	//Including a toString for Parking Office
	@Override
	public String toString() {
		return "ParkingOffice--["+
	             "name='" + name + "\'" +
	             ", address=" + address +
	             ", customers=" + customers +
	             ", cars=" + cars +
	             ", lots=" + lots +
	             ", charges=" + charges +
	             "]";
	}
	
	// Equals method used to compare ParkingOffices
	// Assists in identifying duplicates
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		ParkingOffice  that  =(ParkingOffice) o;
		return name.equals(that.name) && //.equals plays role in being able to compare objects used in collections
				address.equals(that.address);
	}
	
	// Creates a specific HashCode that can be used for comparison
	@Override
	public int hashCode() { //plays role in being able to compare objects used in collections
		return java.util.Objects.hash(name,address);
	}
}

/*
 * References:
 * 
 * GeeksforGeeks. 2018. “Collectors toSet() in Java with Examples.” GeeksforGeeks. 
 * Updated December 06, 2018. https://www.geeksforgeeks.org/collectors-toset-in-java-with-examples/.
 * 
 * 
 */
